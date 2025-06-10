package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.MetodoPago;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.MetodoPagoRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseGenericObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    private static ResponseGenericObject<List<MetodoPago>> rgObjList;
    private static ResponseGenericObject<MetodoPago> rgObj;

    public ResponseGenericObject<List<MetodoPago>> obtenerMetodosPago(){
        rgObjList = new ResponseGenericObject<>();
        List<MetodoPago> metodosPago = (List<MetodoPago>) metodoPagoRepository.findAll();
        if(!metodosPago.isEmpty()){
            rgObjList.setAsSuccessfully("Consulta ejecutada con exito", metodosPago);
            return rgObjList;        
        }
        rgObjList.setAsNotSuccessfully("No hay metodos de pago creados");   
        return rgObjList;
    }

    public ResponseGenericObject<MetodoPago> obtenerMetodoPagoPorId(int idMetodoPago){
        rgObj = new ResponseGenericObject<>();
        Optional<MetodoPago> metodoPagoEncontrado = metodoPagoRepository.findById(idMetodoPago);
        if(metodoPagoEncontrado.isPresent()){
            rgObj.setAsSuccessfully("Metodo de pago encontrado", metodoPagoEncontrado.get());
            return rgObj;
        }
        rgObj.setAsNotSuccessfully("El metodo de pago con id ".concat(idMetodoPago+"").concat(" no existe"));
        return rgObj;
    }

    public ResponseGenericObject<MetodoPago> crearNuevo(MetodoPago metodoPago){
        rgObj = new ResponseGenericObject<>();
        List<MetodoPago> metodoPagos = metodoPagoRepository.findByNombreMetodoPago(metodoPago.getNombreMetodoPago());
        if(metodoPagos.isEmpty()){            
            rgObj.setAsSuccessfully("Metodo de pago guardado con éxito", metodoPagoRepository.save(metodoPago));
            return rgObj;       
        }       
        rgObj.setAsNotSuccessfully("El metodo de pago ".concat(metodoPago.getNombreMetodoPago()).concat(" ya existe"));
        return rgObj;
    }
}

