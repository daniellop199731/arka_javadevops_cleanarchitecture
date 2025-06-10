package com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.CarritoCompra;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.CarritoCompraProducto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;

@Repository
public interface CarritoCompraProductoRepository extends CrudRepository<CarritoCompraProducto, Integer> {

    List<CarritoCompraProducto> findByCarritoCompra(CarritoCompra carritoCompra);
    
    List<CarritoCompraProducto> findByProductoCarritoCompra(Producto productoCarritoCompra);

}

