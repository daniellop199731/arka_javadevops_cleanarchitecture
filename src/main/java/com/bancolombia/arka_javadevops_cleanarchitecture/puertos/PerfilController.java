package com.bancolombia.arka_javadevops_cleanarchitecture.puertos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Perfil;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.PerfilService;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseGenericObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/usuarios/perfiles")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping("")
    public ResponseEntity<ResponseGenericObject<List<Perfil>>> obtenerPerfiles() {
        ResponseGenericObject<List<Perfil>> response = perfilService.obtenerPerfiles();
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{idPerfil}")
    public ResponseEntity<ResponseGenericObject<Perfil>> obtenerPerfilPorId(@PathVariable int idPerfil) {
        ResponseGenericObject<Perfil> response = perfilService.obtenerPerfilPorId(idPerfil);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/crearNuevo")
    public ResponseEntity<ResponseGenericObject<Perfil>> crearNuevoPerfil(@Valid @RequestBody Perfil perfil) {
        ResponseGenericObject<Perfil> response = perfilService.creaNuevoPerfil(perfil);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    

}

