package com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.MetodoPagoUsuario;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Usuario;

@Repository
public interface MetodoPagoUsuarioRepository extends CrudRepository<MetodoPagoUsuario, Integer> {

    List<MetodoPagoUsuario> findByUsuarioMetodoPago(Usuario usuario);

}
