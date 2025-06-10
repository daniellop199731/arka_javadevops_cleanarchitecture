package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.Objects;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.dtos.ProductoDTO;
import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.mappers.ProductoMapper;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.ExpirableProducto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.ProductoRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces.ProductoServiceCreate;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces.ProductoServiceUtils;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Primary
public class ProductoServiceCreateImp implements ProductoServiceCreate {

    private final ProductoRepository productoRepository;

    private final ProductoServiceUtils productoServiceUtils;

    private final ProductoMapper productoMapper;

    private static ResponseObject rObj;
    
    public ResponseObject crearNuevo(Producto producto){
        rObj = new ResponseObject();
        if(productoServiceUtils.existeProductoPorReferencia(producto.getReferenciaProducto())){
            rObj.setAsNotSuccessfully("Ya existe un producto con la referencia ".concat(producto.getReferenciaProducto()));
            return rObj;
        }
        rObj.setAsSuccessfully("Producto guardado con exito", productoRepository.save(producto));
        return rObj;
    }

    //PASO 5: Crear nuevo servicio o modificar el existente para en los parametros
    //se tenga la calse extendida
    //SIGUIENTE PASO: Ver ProductoController.java
    public ResponseObject crearNuevoV2(ExpirableProducto producto){
        rObj = new ResponseObject();
        Producto productoToPersist = null;
        if(Objects.isNull(producto.getFechaExpiracion())){
            //productoToPersist = productoMapper.fromDTO((ProductoDTO) producto);
            productoToPersist = (Producto) producto;
        } else {
            productoToPersist = producto;
        }

        ProductoDTO productoToReturn = null;
        Producto resultDB = productoRepository.save(productoToPersist);
        if(resultDB instanceof ExpirableProducto){
            productoToReturn = productoMapper.toDto((ExpirableProducto) resultDB);
        } else {
            productoToReturn = productoMapper.toDto(resultDB);
        }
        rObj.setAsSuccessfully("Producto creado con exito", productoToReturn);
        return rObj;

    }

    public ResponseObject crearNuevoDto(Producto producto){
        rObj = new ResponseObject();
        if(productoServiceUtils.existeProductoPorReferencia(producto.getReferenciaProducto())){
            rObj.setAsNotSuccessfully("Ya existe un producto con la referencia ".concat(producto.getReferenciaProducto()));
            return rObj;
        }
        productoRepository.save(producto);
        rObj.setAsSuccessfully("Producto guardado con exito", productoMapper.toDto(producto));
        return rObj;
    }    

}

