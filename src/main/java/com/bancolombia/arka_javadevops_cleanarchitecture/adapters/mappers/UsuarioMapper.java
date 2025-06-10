package com.bancolombia.arka_javadevops_cleanarchitecture.adapters.mappers;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.dtos.ExpirableUsuarioDTO;
import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.dtos.UsuarioDTO;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.ExpirableUsuario;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Usuario;

/// Ejemplo de mapper automatico
@Component
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);

    List<UsuarioDTO> usuariosToUsuariosDto(List<Usuario> usuarios);

    @InheritInverseConfiguration
    Usuario usuarioDtoToUsuario (UsuarioDTO usuarioDTO);

    //PASO 3: Se crea metodo mapper que convierte de ExpirableUsuarioDTO a Usuario
    //Siguiente paso: ver UsuarioService
    ExpirableUsuario usuarioDTOToExpirableUsuario (UsuarioDTO usuarioDTO);    

    List<ExpirableUsuarioDTO> usuariosToExpirableUsuarios(List<ExpirableUsuario> usuarios);
    
}

/// Ejemplo de mapper manual
/*import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bancolombia.arka_javadevops.DTO.UsuarioDTO;
import com.bancolombia.arka_javadevops.models.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDto(Usuario usuario){
        if(usuario == null){
            return null;
        }

        return new UsuarioDTO(
            usuario.getIdentificacionUsuario()
            ,usuario.getCorreoElectronicoUsuario()
            ,usuario.getNombresUsuario()
            ,usuario.getApellidosUsuario()
            ,usuario.getDireccionDespachoUsuario()
            ,usuario.getNicknameUsuario()
        );
    }

    public List<UsuarioDTO> toDto(List<Usuario> usuarios){
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosDTO.add(toDto(usuario));
        }
        return usuariosDTO;
    }

}*/

