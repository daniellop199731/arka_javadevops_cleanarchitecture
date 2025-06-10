package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Categoria;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.ProductoRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces.ProductoServiceRead;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

//PASO 2: Se crea la implementacion de la interfac creada en el paso 1
//Siguiente paso: Ver ProductoController.java
@Service
@RequiredArgsConstructor
@Primary
public class ProductoServiceReadImp implements ProductoServiceRead {

    private final ProductoRepository productoRepository;

    private static ResponseObject rObj;

    public ResponseObject obtenerProductos(){
        rObj = new ResponseObject();
        List<Producto> productos = (List<Producto>) productoRepository.findAll();
        if(productos.isEmpty()){
            rObj.setAsNotSuccessfully("En el momento no hay productos para mostrar");
            return rObj;
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito", productos);
        return rObj;
    }

    public ResponseObject obtenerProductoPorId(int idProducto){
        rObj = new ResponseObject();
        Optional<Producto> producto = productoRepository.findById(idProducto);
        if(producto.isPresent()){
            rObj.setAsSuccessfully("Producto encontrado", producto.get());
            return rObj;
        }
        rObj.setAsNotSuccessfully("No se encontró el producto con id ".concat(idProducto+""));
        return rObj;
    }

    public ResponseObject productosNombreDescripcion(String texto){
        rObj = new ResponseObject();
        List<Producto> productos = productoRepository.productosNombreDescripcion(texto);
        if(productos.isEmpty()){
            rObj.setAsNotSuccessfully("No se encontraron productos");
            return rObj;
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito", productos);
        return rObj;
    }

    public ResponseObject productosOrdenadosAsc(){
        rObj = new ResponseObject();
        List<Producto> productos = (List<Producto>) productoRepository.productosOrdenadosAsc();
        if(productos.isEmpty()){
            rObj.setAsNotSuccessfully("En el momento no hay productos para mostrar");
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito", productos);
        return rObj;
    }    

    public ResponseObject productosPorRangoPrecio(int precioMinimo, int precioMaximo){
        rObj = new ResponseObject();
        List<Producto> productos = (List<Producto>) productoRepository.productosPorRangoPrecio(precioMinimo, precioMaximo);
        if(productos.isEmpty()){
            rObj.setAsNotSuccessfully("En el momento no hay productos para mostrar");
            return rObj;
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito", productos);
        return rObj;
    }    

    public ResponseObject productosPorCategoria(int idCategoria){
        rObj = new ResponseObject();
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        if(productos.isEmpty()){
            rObj.setAsNotSuccessfully("No hay productos de esa categoria");
            return rObj;
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito", productos);
        return rObj;

    }    

}

