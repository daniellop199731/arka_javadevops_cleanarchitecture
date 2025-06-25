package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.mappers.CarritoCompraProductoMapper;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.CarritoCompra;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.CarritoCompraProducto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.CarritoCompraProductoRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseGenericObject;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.TypeModStock;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoCompraProductoService {

    private final CarritoCompraProductoRepository carritoCompraProductoRepository;

    private final UsuarioService usuarioService;
    private final ProductoServicePp productoService;
    private final ProductoServiceModImp productoServiceModImp;
    private final CarritoCompraService carritoCompraService;
    private final CarritoCompraProductoMapper carritoCompraProductoMapper;

    private static ResponseObject rObj;
    private static ResponseGenericObject<List<CarritoCompraProducto>> rgObjCarritoCompraProductos;
    

    public ResponseObject obtenerProductosCarrito(int idCarrito){
        rObj = new ResponseObject();
        CarritoCompra carritoCompra = new CarritoCompra();
        carritoCompra.setIdCarritoCompra(idCarrito);
        List<CarritoCompraProducto> carritoCompraProductos = carritoCompraProductoRepository.findByCarritoCompra(carritoCompra);
        if(carritoCompraProductos.isEmpty()){
            rObj.setAsNotSuccessfully("El carrito o no existe no tiene productos");
            return rObj;
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito"
            , carritoCompraProductoMapper.toDTO(carritoCompraProductos));
        return rObj;
    }

    public ResponseGenericObject<List<CarritoCompraProducto>> obtenerProductosCarritoWithoutDTO(int idCarrito){
        rgObjCarritoCompraProductos = new ResponseGenericObject<>();
        CarritoCompra carritoCompra = new CarritoCompra();
        carritoCompra.setIdCarritoCompra(idCarrito);
        List<CarritoCompraProducto> carritoCompraProductos = carritoCompraProductoRepository.findByCarritoCompra(carritoCompra);
        if(carritoCompraProductos.isEmpty()){
            rgObjCarritoCompraProductos.setAsNotSuccessfully("El carrito o no existe no tiene productos");
            return rgObjCarritoCompraProductos;
        }
        rgObjCarritoCompraProductos.setAsSuccessfully("Consulta ejecutada con exito"
            , carritoCompraProductos);
        return rgObjCarritoCompraProductos;
    }    

    public ResponseObject carritosPorProducto(int idProducto){
        rObj = productoService.obtenerProductoPorId(idProducto);
        if(rObj.isSuccessfully()){
            List<CarritoCompraProducto> carritoCompraProductos = carritoCompraProductoRepository.findByProductoCarritoCompra((Producto) rObj.getObj());
            if(carritoCompraProductos.isEmpty()){
                rObj.setAsNotSuccessfully("No se encontraron carritos con el producto");
                return rObj;
            }
            rObj.setAsSuccessfully("Carritos encontrados", carritoCompraProductos);
        }
        return rObj;
    }

    public ResponseObject eliminarProductosCarrito(int idCarrito){
        rObj = new ResponseObject();
        rgObjCarritoCompraProductos = this.obtenerProductosCarritoWithoutDTO(idCarrito);
        if(rgObjCarritoCompraProductos.isSuccessfully()){

            for (CarritoCompraProducto carritoCompraProducto : rgObjCarritoCompraProductos.getObj()) {
                rObj = productoServiceModImp.modificarUnidadesStock(
                    carritoCompraProducto.getProductoCarritoCompra().getIdProducto()
                    , carritoCompraProducto.getProductoCarritoCompra()
                    , carritoCompraProducto.getUnidadesProducto()
                    , TypeModStock.INCRESE);

                if(!rObj.isSuccessfully()){
                    return rObj;
                }
            }

            carritoCompraProductoRepository.deleteAll(rgObjCarritoCompraProductos.getObj());
            rObj.setAsSuccessfully("Productos eliminados con exito", null);
        }
        return rObj;
    }    

    public ResponseObject agregarProductosCarrito(int idUsuario, List<CarritoCompraProducto> carritoCompraProductos){

        rObj = new ResponseObject();

        rObj = usuarioService.obtenerUsuarioPorIdWitOutDto(idUsuario);
        if(!rObj.isSuccessfully()){
            return rObj;
        }

        if(carritoCompraProductos.size() <= 0){
            rObj.setAsNotSuccessfully("No hay productos para agregar");
            return rObj;
        }

        rObj = carritoCompraService.carritoActual(idUsuario);
        if(!rObj.isSuccessfully()){
            rObj = carritoCompraService.crearNuevo(idUsuario);
        }      
        
        if(rObj.isSuccessfully()){        

            CarritoCompra carritoCompraActual = (CarritoCompra) rObj.getObj();
            List<CarritoCompraProducto> productosParaCarrito = new ArrayList<>();
            
            rObj = this.eliminarProductosCarrito(carritoCompraActual.getIdCarritoCompra());
            if(rObj.isSuccessfully()){
                Producto producto = null;
                for (CarritoCompraProducto carritoCompraProducto : carritoCompraProductos) {
                    rObj = productoService.obtenerProductoPorId(carritoCompraProducto.getProductoCarritoCompra().getIdProducto());

                    if(!rObj.isSuccessfully()){
                        return rObj;
                    }

                    producto = (Producto) rObj.getObj();
                    if(carritoCompraProducto.getUnidadesProducto() <= 0){                    
                        rObj.setAsNotSuccessfully(
                            "Para el producto con id ".concat(producto.getIdProducto()+"")
                            .concat(" no se asignaron unidades")
                        );
                        return rObj;      
                    }

                    if(carritoCompraProducto.getUnidadesProducto() > producto.getStockProducto()){
                        rObj.setAsNotSuccessfully("No hay suficioentes unidades para el producto "
                        .concat(producto.getNombreProducto()));
                        return rObj;
                    }

                    rObj = productoServiceModImp.modificarUnidadesStock(
                        producto.getIdProducto()
                        , producto
                        , carritoCompraProducto.getUnidadesProducto()
                        , TypeModStock.DECRESE);
                    if(!rObj.isSuccessfully()){
                        return rObj; 
                    }                

                    carritoCompraProducto.setCarritoCompra(carritoCompraActual);
                    carritoCompraProducto.setProductoCarritoCompra(producto);
                    productosParaCarrito.add(carritoCompraProducto);

                }
                rObj.setAsSuccessfully("Productos agregados con exito"
                , carritoCompraProductoMapper.toDTO(
                    (List<CarritoCompraProducto>) carritoCompraProductoRepository.saveAll(productosParaCarrito)
                    )
                );                
            }            
        }
        return rObj;
    }

}

