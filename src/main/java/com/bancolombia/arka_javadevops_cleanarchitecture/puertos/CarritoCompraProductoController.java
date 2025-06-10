package com.bancolombia.arka_javadevops_cleanarchitecture.puertos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.CarritoCompraProducto;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.CarritoCompraProductoService;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/ecommerce/carritoComprasProducto")
@RequiredArgsConstructor
public class CarritoCompraProductoController {

    private final CarritoCompraProductoService carritoCompraProductoService;

    @GetMapping("/{idCarrito}")
    public ResponseEntity<ResponseObject> obtenerProductosCarrito(@PathVariable int idCarrito) {
        ResponseObject response = carritoCompraProductoService.obtenerProductosCarrito(idCarrito);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /// Integración de Relaciones en Proyecto Arka [Actividad Requerida]
    /// 
    //Búsqueda de Pedidos por producto
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<ResponseObject> carritosPorProducto(@PathVariable int idProducto) {
        ResponseObject response = carritoCompraProductoService.carritosPorProducto(idProducto);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);    
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /// Integración de Relaciones en Proyecto Arka [Actividad Requerida]
    

    @PostMapping("/agregarProducto/{idUsuario}")
    public ResponseEntity<ResponseObject> agregarProductoCarrito(
        @PathVariable(required = true) int idUsuario
        , @RequestBody List<CarritoCompraProducto> carritoCompraProductos    
    ) { 
        ResponseObject response = carritoCompraProductoService.agregarProductoCarrito(
                idUsuario, carritoCompraProductos);

        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    

}

