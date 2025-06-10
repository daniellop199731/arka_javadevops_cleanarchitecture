package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.ProductoRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces.ProductoServiceMod;
import com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces.ProductoServiceUtils;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Primary
public class ProductoServiceModImp implements ProductoServiceMod {

    private final ProductoRepository productoRepository;
    private final ProductoServiceUtils productoServiceUtils;

    private static ResponseObject rObj;    

    public ResponseObject actualizar(int idProducto, Producto producto){
        rObj = new ResponseObject();
        Optional<Producto> productoEncontrado = productoRepository.findById(idProducto);
        if(productoEncontrado.isPresent()){
            producto.setIdProducto(idProducto);
            if(productoServiceUtils.existeProductoPorReferenciaParaActualizar(producto)){
                rObj.setAsNotSuccessfully("Ya existe un producto con la referencia ".concat(producto.getReferenciaProducto()));
                return rObj;
            }                

            rObj.setAsSuccessfully("Producto actualizado con exito", productoRepository.save(producto));
            return rObj;                             
        }      

        rObj.setAsNotSuccessfully("El producto a actualizar no existe");
        return rObj;
    }

    public ResponseObject eliminar(int idProducto){
        rObj = new ResponseObject();
        Optional<Producto> producto = productoRepository.findById(idProducto);
        if(producto.isPresent()){
            productoRepository.deleteById(idProducto);
            rObj.setAsSuccessfully("El Producto "
                .concat(producto.get().getNombreProducto())
                .concat("-")
                .concat(producto.get().getIdProducto()+"")
                .concat(" fue eliminado exitosamente"), producto);  
        }
        rObj.setAsNotSuccessfully("No se encontró el producto para ser eliminado");
        return rObj;
    }

    public ResponseObject descontarUnidadesStock(int idProducto, Producto producto, int unidades){
        rObj = new ResponseObject();
        producto.setStockProducto(producto.getStockProducto()-unidades);
        rObj = this.actualizar(idProducto, producto);
        if(rObj.isSuccessfully()){
            producto = (Producto) rObj.getObj();
            if(producto.getStockProducto() <= producto.getStockMinimoProducto()){
                System.out.println("Crea notificacion de abastecimineto");
            }
        }
        return rObj;
    }    

}

