package com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.TypeModStock;

public interface ProductoServiceMod {

    ResponseObject actualizar(int idProducto, Producto producto);

    ResponseObject eliminar(int idProducto);

    ResponseObject modificarUnidadesStock(int idProducto, Producto producto, int unidades, TypeModStock typeModStock);

}

