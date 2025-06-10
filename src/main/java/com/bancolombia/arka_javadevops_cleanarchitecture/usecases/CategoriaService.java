package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Categoria;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.CategoriaRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static ResponseObject rObj;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public ResponseObject obtenerCategorias(){
        rObj = new ResponseObject();
        List<Categoria> categorias = (List<Categoria>) categoriaRepository.findAll(); 
        if(categorias.isEmpty()){
            rObj.setAsNotSuccessfully("No hay categorías de productos creadas");            
            return rObj;
        }
        
        rObj.setAsSuccessfully("Consulta ejecutada con exito", categorias);
        return rObj;
    }

    public ResponseObject obtenerCategoriasPorNombre(String nombreCategoria){
        rObj = new ResponseObject();
        List<Categoria> categorias = categoriaRepository.findByNombreCategoriaLike("%"+nombreCategoria+"%");
        if(categorias.isEmpty()){
            rObj.setAsNotSuccessfully("No se encontraron categorias");
            return rObj;
        }        
        rObj.setAsSuccessfully("Categorias encontradas", categorias);            
        return rObj;
    }

    public ResponseObject crearNueva(Categoria categoria){
        rObj = new ResponseObject();
        List<Categoria> categorias = categoriaRepository.findByNombreCategoria(categoria.getNombreCategoria());
        if(categorias.size() > 0){
            rObj.setAsNotSuccessfully("La categoria ".concat(categoria.getNombreCategoria())
                .concat(" ya existe"));
            return rObj;
        }
        rObj.setAsSuccessfully("Categoria guardada con éxito", categoriaRepository.save(categoria));
        return rObj;
    }

    public ResponseObject actualizarCategoria(Categoria categoria){
        rObj = new ResponseObject();
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(categoria.getIdCategoria());
        if(categoriaEncontrada.isPresent()){
            rObj.setAsSuccessfully("Categoria actualizada con exito", categoriaRepository.save(categoria));
            return rObj;
        }
        rObj.setAsNotSuccessfully("La categoria a actualizar no existe");
        return rObj;
    }

}

