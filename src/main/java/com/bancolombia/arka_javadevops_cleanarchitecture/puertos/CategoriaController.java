package com.bancolombia.arka_javadevops_cleanarchitecture.puertos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Categoria;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.CategoriaService;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/inventario/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> obtenerCategorias() {
        ResponseObject response = categoriaService.obtenerCategorias();
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/busquedaPorNombre/{nombreCategoria}")
    public ResponseEntity<ResponseObject> obtenerCategoriasPorNombre(@PathVariable String nombreCategoria) {
        ResponseObject response = categoriaService.obtenerCategoriasPorNombre(nombreCategoria);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/crearNueva")
    public ResponseEntity<ResponseObject> crearNueva(@Valid @RequestBody Categoria categoria) {
        ResponseObject response = categoriaService.crearNueva(categoria);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(categoriaService.crearNueva(categoria), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/actualizar")
    public ResponseEntity<ResponseObject> actualizar(@Valid @RequestBody Categoria categoria) {
        ResponseObject response = categoriaService.actualizarCategoria(categoria);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(categoriaService.actualizarCategoria(categoria), HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
}

