package com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.ExpirableProducto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

public interface ProductoServiceCreate {

    ResponseObject crearNuevo(Producto producto);

    ResponseObject crearNuevoV2(ExpirableProducto producto);

    ResponseObject crearNuevoDto(Producto producto);    

}
