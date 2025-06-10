package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.mappers.CarritoCompraMapper;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.CarritoCompra;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.EstadoDespacho;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Usuario;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.CarritoCompraRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoCompraService {

    private final CarritoCompraRepository carritoCompraRepository;
    private final UsuarioService usuarioService;
    private final CarritoCompraMapper carritoCompraMapper;

    private static ResponseObject rObj;

    public ResponseObject crearNuevo(int idUsuario){
        rObj = usuarioService.obtenerUsuarioPorIdWitOutDto(idUsuario);
        if(rObj.isSuccessfully()){
            CarritoCompra carritoCompra = new CarritoCompra();
            EstadoDespacho estadoDespacho = new EstadoDespacho();
            estadoDespacho.setIdEstadoDespacho(1);
            carritoCompra.setUsuarioCarritoCompra((Usuario) rObj.getObj());            
            carritoCompra.setEstadoDespacho(estadoDespacho);
            rObj.setAsSuccessfully("Carrito de compras creado", carritoCompraRepository.save(carritoCompra));
            return rObj;
        }
        return rObj;
    }

    public ResponseObject obtenerCarritoPorId(int idCarritoCompra){
        rObj = new ResponseObject();
        Optional<CarritoCompra> carritoCompraEncontrado = carritoCompraRepository.findById(idCarritoCompra);
        if(carritoCompraEncontrado.isPresent()){
            rObj.setAsSuccessfully("Carrito de compra encontrado", carritoCompraEncontrado.get());
            return rObj;
        }
        rObj.setAsNotSuccessfully("El carrito de compra con id ".concat(idCarritoCompra+"").concat(" no existe"));
        return rObj;
    }

    public ResponseObject caarritosPorUsuario(int idUsuario){
        rObj = usuarioService.obtenerUsuarioPorIdWitOutDto(idUsuario);
        if(!rObj.isSuccessfully()){
            return rObj;
        }
        List<CarritoCompra> carritoCompras = carritoCompraRepository.findByUsuarioCarritoCompra((Usuario) rObj.getObj());
        rObj.setAsSuccessfully("Carritos encontrados", carritoCompraMapper.toDto(carritoCompras));
        return rObj;
    }

    public ResponseObject carritoActual(int idUsuario){
        rObj = new ResponseObject();
        List<CarritoCompra> carritosCompra = carritoCompraRepository.findCarritoActual(idUsuario);
        if(carritosCompra.size() == 0){
            rObj.setAsNotSuccessfully("No hay carrito de compras actual");
            return rObj;
        }
        rObj.setAsSuccessfully("Carrito actual encontrado", carritosCompra.get(0));
        return rObj;    
    }

    public ResponseObject carritosAbandonados(){
        rObj = new ResponseObject();
        List<CarritoCompra> carritoCompras = carritoCompraRepository.carritosAbandonados();
        rObj.setAsSuccessfully();
        if(carritoCompras.isEmpty()){            
            rObj.setAsNotSuccessfully("No hay carritos abandonados");
            return rObj;
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito", carritoCompraMapper.toDto(carritoCompras));
        return rObj;
    }

    public ResponseObject carritoComprasPorFechas(String minDate, String maxDate){
        rObj = new ResponseObject();
        try{
            List<CarritoCompra> carritoCompras = carritoCompraRepository.findByFechaCreacionCarritoCompraBetween(
                new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(minDate+" 00:00")
                , new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(maxDate+" 11:59"));
            if(carritoCompras.size() == 0){
                rObj.setAsNotSuccessfully("No hay carritos entre el rango de fechas");
                return rObj;
            }

            rObj.setAsSuccessfully("Consulta ejecutada con exito", carritoCompraMapper.toDto(carritoCompras));
            return rObj;

        } catch (Exception ex){
            rObj.setAsNotSuccessfully("Error al ejecutar: ".concat(ex.getMessage()));
            return rObj;
        }
    }

}

