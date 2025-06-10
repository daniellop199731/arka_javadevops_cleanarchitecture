package com.bancolombia.arka_javadevops_cleanarchitecture.puertos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.CarritoCompraService;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/ecommerce/carritosCompra")
@RequiredArgsConstructor
public class CarritoCompraController {

    private final CarritoCompraService carritoCompraService;

    @GetMapping("/{idCarritoCompra}")
    public ResponseEntity<ResponseObject> obtenerCarritoPorId(@PathVariable int idCarritoCompra) {
        ResponseObject response = carritoCompraService.obtenerCarritoPorId(idCarritoCompra);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/carritoActual/{idUsuario}")
    public ResponseEntity<ResponseObject> carritoActual(@PathVariable int idUsuario) {
        ResponseObject response = carritoCompraService.carritoActual(idUsuario);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /// Integración de Relaciones en Proyecto Arka [Actividad Requerida]
    
    //Obtener carritos abandonados
    @GetMapping("/abandonados")
    public ResponseEntity<ResponseObject> carritosAbandonados() {
        ResponseObject response = carritoCompraService.carritosAbandonados();
        if (response.isSuccessfully()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Búsqueda de Pedidos en un rango de fechas
    @GetMapping("")
    public ResponseEntity<ResponseObject> carritoComprasPorFechas(
        @RequestParam(required = true) String minDate
        , @RequestParam(required = true) String maxDate) {
        ResponseObject response = carritoCompraService.carritoComprasPorFechas(minDate, maxDate);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //Historial de Pedidos de un Cliente
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ResponseObject> carritosUsuario(@PathVariable int idUsuario) {
        ResponseObject response = carritoCompraService.caarritosPorUsuario(idUsuario);
        if(response.isSuccessfully()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /// Integración de Relaciones en Proyecto Arka [Actividad Requerida]
    

}

