package com.bancolombia.arka_javadevops_cleanarchitecture.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;
import com.bancolombia.arka_javadevops_cleanarchitecture.entities.repositories.ProductoRepository;
import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

public class ProductoServiceCreate {

    //Mock:Instancia falsa que simulara la accion real, a la cual en cada test se define que es lo que retorna
    /*
    Para este caso se crea un Mock de ProductoRepository, y para los metodos que se probaran de 
    ProductoServiceCreateImp se define el retorno de lo que se use de ProductoRepository
    */
    @Mock
    ProductoRepository productoRepository;

    /*
    Tambien se tiene la dependencia de ProductoServiceUtilsImp, por eso tambien se debe 
    hacer un Mock
    */
    @Mock
    ProductoServiceUtilsImp productoServiceUtilsImp;

    /*
    Como lo que se va a estar probando son los metodos de ProductoServiceCreateImp
    se injecta a la dependencia que se tiene de ProductoRepository el Mock que se 
    creo anteriormente
    */
    @InjectMocks
    ProductoServiceCreateImp productoServiceCreateImp;

    //Procesa las anotaciones creadas
    //Para este caso, crea la instancia falsa (proxy) de usuarioRepository
    //luego le inyecta la instancia falsa a usuarioController
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    //Anotacion para indicar el que es un test
    @Test
    public void testCrearNuevoExitoso(){
        //Definicion de los metodos que se mockearon
        when(productoRepository.save(any()))
            .thenReturn(new Producto(1, "ref01", "producto", "descripcion", 1.0, 1, 1, null, 1, null
                ,null, null ));
        
        when(productoServiceUtilsImp.existeProductoPorReferencia(anyString()))
            .thenReturn(false);
        //Definicion de los metodos que se mockearon            

        //En response se almacena el resultado al llamar a crearNuevo, el metodo funcionara
        //con los Mocks creados
        ResponseObject response = productoServiceCreateImp.crearNuevo(
            new Producto(1, "ref01", "producto", "descripcion", 1.0, 1, 1, null, 1, null
                ,null, null )
        );

        //Se espera que response no es null
        assertNotNull(response);
        //Se espera que successfully de ResponseObject sea true
        assertTrue(response.isSuccessfully());
        //Se espera que msj de ResponseObject sea "Producto guardado con exito"
        assertEquals("Producto guardado con exito", response.getMsj());
        //Se espera que obj de ResponseObject no sea null
        assertNotNull(response.getObj());
    }

}
