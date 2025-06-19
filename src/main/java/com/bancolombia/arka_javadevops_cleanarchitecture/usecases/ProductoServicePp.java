package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.dtos.ProductoDTO;
import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.mappers.ProductoMapper;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Categoria;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.ExpirableProducto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.ProductoRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

//PASO 0: Se cambia el nombre del servicio original, el indicativo Pp
//Indica que es el servicio principal y alverga todos los servicios de productos
//SIGUIENTE PASO: Ver ProductoServiceConsultas.java

@Service
@RequiredArgsConstructor
public class ProductoServicePp {

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    private static ResponseObject rObj;

    public ResponseObject obtenerProductos(){
        rObj = new ResponseObject();
        List<Producto> productos = (List<Producto>) productoRepository.findAll();
        if(productos.isEmpty()){
            rObj.setMsj("En el momento no hay productos para mostrar");
        } else {
            rObj.setMsj("Consulta ejecutada con exito");
            rObj.setAsSuccessfully();
            rObj.setObj(productos);
        }
        return rObj;
    }

    public ResponseObject obtenerProductoPorId(int idProducto){
        rObj = new ResponseObject();
        Optional<Producto> producto = productoRepository.findById(idProducto);
        if(producto.isPresent()){
            rObj.setAsSuccessfully("Producto encontrado", producto.get());
            return rObj;
        }
        rObj.setMsj("No se encontró el producto con id ".concat(idProducto+""));        
        return rObj;
    }

    public ResponseObject productosNombreDescripcion(String texto){
        rObj = new ResponseObject();
        List<Producto> productos = productoRepository.productosNombreDescripcion(texto);
        if(productos.isEmpty()){
            rObj.setMsj("No se encontraron productos");
        } else {
            rObj.setMsj("Productos encontrados");
            rObj.setAsSuccessfully();
            rObj.setObj(productos);
        }
        return rObj;
    }

    public ResponseObject productosOrdenadosAsc(){
        rObj = new ResponseObject();
        List<Producto> productos = (List<Producto>) productoRepository.productosOrdenadosAsc();
        if(productos.isEmpty()){
            rObj.setMsj("En el momento no hay productos para mostrar");
        } else {
            rObj.setMsj("Consulta ejecutada con exito");
            rObj.setAsSuccessfully();
            rObj.setObj(productos);
        }
        return rObj;
    }    

    public ResponseObject productosPorRangoPrecio(int precioMinimo, int precioMaximo){
        rObj = new ResponseObject();
        List<Producto> productos = (List<Producto>) productoRepository.productosPorRangoPrecio(precioMinimo, precioMaximo);
        if(productos.isEmpty()){
            rObj.setMsj("En el momento no hay productos para mostrar");
        } else {
            rObj.setMsj("Consulta ejecutada con exito");
            rObj.setAsSuccessfully();
            rObj.setObj(productos);
        }
        return rObj;
    }     

    public ResponseObject crearNuevo(Producto producto){
        rObj = new ResponseObject();
        if(this.existeProductoPorReferencia(producto.getReferenciaProducto())){
            rObj.setMsj("Ya existe un producto con la referencia ".concat(producto.getReferenciaProducto()));
            rObj.setObj("");
            return rObj;
        }

        rObj.setObj(productoRepository.save(producto));
        rObj.setMsj("Producto guardado con exito");
        rObj.setAsSuccessfully();

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
        if(this.existeProductoPorReferencia(producto.getReferenciaProducto())){
            rObj.setMsj("Ya existe un producto con la referencia ".concat(producto.getReferenciaProducto()));
            rObj.setObj("");
            return rObj;
        }

        productoRepository.save(producto);
        rObj.setObj(productoMapper.toDto(producto));
        rObj.setMsj("Producto guardado con exito");
        rObj.setAsSuccessfully();

        return rObj;
    }

    public ResponseObject actualizar(int idProducto, Producto producto){
        rObj = new ResponseObject();
        Optional<Producto> productoEncontrado = productoRepository.findById(idProducto);
        if(productoEncontrado.isPresent()){
            producto.setIdProducto(idProducto);
            if(this.existeProductoPorReferenciaParaActualizar(producto)){
                rObj.setMsj("Ya existe un producto con la referencia ".concat(producto.getReferenciaProducto()));
                rObj.setObj("");
                return rObj;
            }                

            rObj.setAsSuccessfully(); 
            rObj.setMsj("Producto actualizado con exito");
            rObj.setObj(productoRepository.save(producto));
            return rObj;                             
        }      

        rObj.setMsj("El producto a actualizar no existe");
        return rObj;
    }

    public ResponseObject eliminar(int idProducto){
        rObj = new ResponseObject();
        Optional<Producto> producto = productoRepository.findById(idProducto);
        if(producto.isPresent()){
            productoRepository.deleteById(idProducto);
            rObj.setMsj("El Producto "
                .concat(producto.get().getNombreProducto())
                .concat("-")
                .concat(producto.get().getIdProducto()+"")
                .concat(" fue eliminado exitosamente"));
            rObj.setAsSuccessfully();
            rObj.setObj(producto);            
        } else {
            rObj.setMsj("No se encontró el producto para ser eliminado");
        }

        return rObj;
    }

    public ResponseObject descontarUnidadesStock(Producto producto, int unidades){
        rObj = new ResponseObject();
        producto.setStockProducto(producto.getStockProducto()-unidades);
        rObj = actualizar(producto.getIdProducto(), producto);
        if(rObj.isSuccessfully()){
            producto = (Producto) rObj.getObj();
            if(producto.getStockProducto() <= producto.getStockMinimoProducto()){
                System.out.println("Crea notificacion de abastecimineto");
            }
        }
        return rObj;
    }

    public ResponseObject productosPorCategoria(int idCategoria){
        rObj = new ResponseObject();
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        rObj.setAsSuccessfully();
        if(productos.isEmpty()){
            rObj.setMsj("No hay productos de esa categoria");
            rObj.setObj(productos);
        }
        rObj.setMsj("Consulta ejecutada con exito");
        rObj.setObj(productos);
        return rObj;

    }

    public boolean existeProductoPorReferencia(String referenciaProducto){
        if(referenciaProducto != null){
            Producto productoPorReferencia = productoRepository.findByReferenciaProducto(referenciaProducto);
            if(productoPorReferencia != null){
                productoPorReferencia = null;
                return true;
            }
        }
        return false;
    }

    public boolean existeProductoPorReferenciaParaActualizar(Producto productoActualizar){
        if(productoActualizar.getReferenciaProducto() != null){
            Producto productoPorReferencia = productoRepository.findByReferenciaProducto(productoActualizar.getReferenciaProducto());
            if(productoPorReferencia != null && 
                ((productoPorReferencia != null ? productoPorReferencia.getIdProducto():0)
                    !=
                    (productoActualizar.getIdProducto())) ){
                productoPorReferencia = null;
                return true;
            }
        }
        return false;
    }

}

