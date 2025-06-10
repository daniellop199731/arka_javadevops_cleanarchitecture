package com.bancolombia.arka_javadevops_cleanarchitecture.usecases.interfaces;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.Producto;

public interface ProductoServiceUtils {

    boolean existeProductoPorReferencia(String referenciaProducto);

    boolean existeProductoPorReferenciaParaActualizar(Producto productoActualizar);

}

