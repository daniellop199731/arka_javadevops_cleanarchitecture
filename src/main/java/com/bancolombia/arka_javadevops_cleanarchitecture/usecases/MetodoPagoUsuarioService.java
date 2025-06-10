package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bancolombia.arka_javadevops_cleanarchitecture.adapters.dtos.UsuarioDTO;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.MetodoPago;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.MetodoPagoUsuario;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Usuario;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.MetodoPagoUsuarioRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseGenericObject;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetodoPagoUsuarioService {

    private final MetodoPagoUsuarioRepository metodoPagoUsuarioRepository;
    private final UsuarioService usuarioService;
    private final MetodoPagoService metodoPagoService;

    private static ResponseObject rObj;
    private static ResponseGenericObject<UsuarioDTO> rgObjUsuarioDto;

    public ResponseObject obtenerMetodosPagosUsuario(int idUsuario){
        rObj = new ResponseObject();
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        List<MetodoPagoUsuario> metodosPagoUsuario = metodoPagoUsuarioRepository.findByUsuarioMetodoPago(usuario);
        rObj.setAsSuccessfully();
        rObj.setObj(metodosPagoUsuario);
        if(metodosPagoUsuario.isEmpty()){
            rObj.setMsj("El usuario no tiene metodos de pago");
        } else {
            rObj.setMsj("Consulta ejecutada con exito");
        }
        return rObj;
    }

    public ResponseObject agregarMetodoPagoUsuario(int idUsuario, int idMetodoPago, double valorCuenta){
        rgObjUsuarioDto = usuarioService.obtenerUsuarioPorId(idUsuario);
        if(!rgObjUsuarioDto.isSuccessfully()){
            return rObj;
        }
        ResponseGenericObject<MetodoPago> rgObj = metodoPagoService.obtenerMetodoPagoPorId(idMetodoPago);
        if(!rgObj.isSuccessfully()){
            return rObj;
        }   
        if(valorCuenta <= 0){
            rObj.setAsNotSuccessfully("El valor debe ser mayor a cero");
            return rObj;
        }
        
        MetodoPagoUsuario metodoPagoUsuario = new MetodoPagoUsuario();
        Usuario usuario = new Usuario();
        MetodoPago metodoPago = new MetodoPago();

        usuario.setIdUsuario(idUsuario);
        metodoPago.setIdMetodoPago(idMetodoPago);
        metodoPagoUsuario.setUsuarioMetodoPago(usuario);
        metodoPagoUsuario.setMetodoPago(metodoPago);
        metodoPagoUsuario.setValorCuentaMetodoPago(valorCuenta);

        rObj.setAsSuccessfully("Metodo de pago agregado", metodoPagoUsuarioRepository.save(metodoPagoUsuario));
        return rObj;
    }

}

