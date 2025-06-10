package com.bancolombia.arka_javadevops_cleanarchitecture.puertos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.MetodoPagoUsuarioService;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/usuarios/metodosPagosUsuario")
@RequiredArgsConstructor
public class MetodoPagoUsuarioController {

    private final MetodoPagoUsuarioService metodoPagoUsuarioService;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ResponseObject> obtenerMetodosPagosUsuario(@PathVariable int idUsuario) {
        ResponseObject response = metodoPagoUsuarioService.obtenerMetodosPagosUsuario(idUsuario);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/agregarMetodoPago/{idUsaurio}/{idMetodo}/{valorCuenta}")
    public ResponseEntity<ResponseObject> agregarMetodoPagoUsuario(@PathVariable(required = true) int idUsaurio
            , @PathVariable(required = true) int idMetodo
            , @PathVariable(required = true) double valorCuenta) {  
        ResponseObject response =  metodoPagoUsuarioService.agregarMetodoPagoUsuario(idUsaurio, idMetodo, valorCuenta); 
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }                        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

