package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Perfil;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.PerfilRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseGenericObject;

import lombok.RequiredArgsConstructor;

@Service //Denota que la clase forma parte de la capa de servicion como un bean de Spring
@RequiredArgsConstructor
public class PerfilService {

    @Autowired  //Indica a Spring que busque una implementacion para esta interfaz
                //Como PerfilRepository extiende de CrudRepository y en esta clase
                //solo se usan metodos de CrudRepository no es necesario tener una clase que 
                //implemente PerfilRepository
    private final PerfilRepository perfilRepository;

    private static ResponseGenericObject<List<Perfil>> rgObjList;
    private static ResponseGenericObject<Perfil> rgObj;

    public ResponseGenericObject<List<Perfil>> obtenerPerfiles(){
        rgObjList = new ResponseGenericObject<>();
        List<Perfil> perfiles = (List<Perfil>) perfilRepository.findAll();
        if(perfiles.isEmpty()){
            rgObjList.setAsNotSuccessfully("No se encontraron perfiles");
            return rgObjList;
        }
        rgObjList.setAsSuccessfully("Consulta ejecutada con exito", perfiles);
        return rgObjList;
    }

    public ResponseGenericObject<Perfil> obtenerPerfilPorId(int idPerfil){
        rgObj = new ResponseGenericObject<>();
        Optional<Perfil> perfil = perfilRepository.findById(idPerfil);
        if(perfil.isPresent()){
            rgObj.setAsSuccessfully("Perfil encontrado", perfil.get());
        }
        rgObj.setAsNotSuccessfully("No se encontro el perfil con id " + idPerfil);
        return rgObj;
    }

    public ResponseGenericObject<Perfil> creaNuevoPerfil(Perfil perfil){
        rgObj = new ResponseGenericObject<>();
        Perfil nuevPerfil = perfilRepository.save(perfil);
        if(nuevPerfil != null){
            rgObj.setAsSuccessfully("Perfil creado con exito", nuevPerfil);
            return rgObj;
        }
        rgObj.setAsNotSuccessfully("El perfil no fue creado");
        return rgObj;
    }

    public ResponseGenericObject<Perfil> eliminarPerfil(int idPerfil){
        rgObj = this.obtenerPerfilPorId(idPerfil);
        if(rgObj.isSuccessfully()){
            perfilRepository.deleteById(idPerfil);
            rgObj.setAsSuccessfully("El perfil fue eliminado con exito", null);
            return rgObj;
        }        
        return rgObj;
    }

    public ResponseGenericObject<Perfil> actualizarPerfil(Perfil perfil){
        rgObj = this.obtenerPerfilPorId(perfil.getIdPerfil());
        if(rgObj.isSuccessfully()){
            rgObj.setAsSuccessfully("El perfil fue actualizado con exito", perfilRepository.save(perfil));
            return rgObj;
        }        
        return rgObj;
    }

}

