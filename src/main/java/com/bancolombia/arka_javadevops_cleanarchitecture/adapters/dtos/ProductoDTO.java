package com.bancolombia.arka_javadevops_cleanarchitecture.adapters.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private int idProducto;
    private String nombreProducto;
    private double precioProducto;
    private String nombreCategoria;
}

