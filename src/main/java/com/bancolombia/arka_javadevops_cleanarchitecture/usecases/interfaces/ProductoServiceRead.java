package com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces;

import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

//PASO 1: Se crea la interfaz que tiene los contratos para los servicios de consultas
//SIGUIENTE PASO: Ver ProductoServiceReadImp.java
public interface ProductoServiceRead {

    ResponseObject obtenerProductos();

    ResponseObject obtenerProductoPorId(int idProducto);

    ResponseObject productosNombreDescripcion(String texto);

    ResponseObject productosOrdenadosAsc();

    ResponseObject productosPorRangoPrecio(int precioMinimo, int precioMaximo);

    ResponseObject productosPorCategoria(int idCategoria);

}

