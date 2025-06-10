package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Proveedor;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.ProveedorRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    private static ResponseObject rObj;

    public ProveedorService() {
    }

    public ResponseObject obtenerProveedores(){
        rObj = new ResponseObject();
        List<Proveedor> proveedores = (List<Proveedor>)proveedorRepository.findAll();
        if(!proveedores.isEmpty()){
            rObj.setAsNotSuccessfully("No hay proveedores registrados");
            return rObj;
        }
        rObj.setAsSuccessfully("Consulta ejecutada con exito", proveedores);
        return rObj;
    }

    public ResponseObject obtenerProveedorPorIdentificacion(String identificacionProveedor){
        rObj = new ResponseObject();
        Proveedor proveedor = proveedorRepository.findByIdentificacionProveedor(identificacionProveedor);
        if(proveedor != null){
            rObj.setAsSuccessfully("Proveedor encontrado", proveedor);
            return rObj;
        } 
        rObj.setAsNotSuccessfully("No existe el proveedor con identificacion: ".concat(identificacionProveedor));
        return rObj;
    }

    public ResponseObject crearNuevo(Proveedor proveedor){
        rObj = new ResponseObject();
        Proveedor proveedorNuevo = (Proveedor) this.obtenerProveedorPorIdentificacion(proveedor.getIdentificacionProveedor()).getObj();
        if(proveedorNuevo != null){
            rObj.setAsNotSuccessfully("El proveedor con identificacion ".concat(proveedor.getIdentificacionProveedor()
                .concat(" ya existe")));
            return rObj;
        }
        rObj.setAsSuccessfully("Proveedor guardado con éxito", proveedorRepository.save(proveedor));
        return rObj;
    }

    public ResponseObject actualizar(Proveedor proveedor){
        rObj = new ResponseObject();
        Proveedor proveedorEncontrado = (Proveedor) this.obtenerProveedorPorIdentificacion(proveedor.getIdentificacionProveedor()).getObj();
        if(proveedorEncontrado != null && 
            ((proveedorEncontrado != null ? proveedorEncontrado.getIdProveedor():0) 
                != 
                proveedor.getIdProveedor())){
            rObj.setAsNotSuccessfully("El proveedor con identificacion ".concat(proveedor.getIdentificacionProveedor()
                .concat(" ya existe")));
            return rObj;
        }
        rObj.setAsSuccessfully("Proveedor actualizado con éxito", proveedorRepository.save(proveedor));
        return rObj;
    }
    
}
