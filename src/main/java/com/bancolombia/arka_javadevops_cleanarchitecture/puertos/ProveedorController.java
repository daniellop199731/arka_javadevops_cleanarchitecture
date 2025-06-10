package com.bancolombia.arka_javadevops_cleanarchitecture.puertos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Proveedor;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.ProveedorService;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/inventario/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    public ProveedorController() {
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> obtenerProveedores() {
        ResponseObject response = proveedorService.obtenerProveedores();
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{identificacionProveedor}")
    public ResponseEntity<ResponseObject> obtenerProveedorPorIdentificacion(@PathVariable String identificacionProveedor) {
        ResponseObject response = proveedorService.obtenerProveedorPorIdentificacion(identificacionProveedor);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);       
    }

    @PostMapping("/crearNuevo")
    public ResponseEntity<ResponseObject> crearNuevo(@Valid @RequestBody Proveedor proveedor) {
        ResponseObject response = proveedorService.crearNuevo(proveedor);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/actualizar")
    public ResponseEntity<ResponseObject> actualizar(@Valid @RequestBody Proveedor proveedor) {
        ResponseObject response = proveedorService.actualizar(proveedor);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

