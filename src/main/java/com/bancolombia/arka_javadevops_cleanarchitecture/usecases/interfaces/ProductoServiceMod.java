package com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

public interface ProductoServiceMod {

    ResponseObject actualizar(int idProducto, Producto producto);

    ResponseObject eliminar(int idProducto);

    ResponseObject descontarUnidadesStock(int idProducto, Producto producto, int unidades);

}

