package com.bancolombia.arka_javadevops_cleanarchitecture.entities;

import java.util.List;

import com.bancolombia.arka_javadevops_cleanarchitecture.entities.enums.TipoProducto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor

@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("not null")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;

    @Column(nullable = true)
    private String referenciaProducto;

    @NotBlank(message = "Debe proporcionar un nombre al producto")
    @NotNull(message = "Debe proporcionar un nombre al producto")
    private String nombreProducto;

    @NotBlank(message = "Debe proporcionar una descripcion")
    @NotNull(message = "Debe proporcionar una descripcion")
    private String descripcionProducto;

    @NotNull(message = "Debe proporcionar un precio al producto")
    @Positive(message = "El precio del producto no puede ser cero")
    private double precioProducto;

    @NotNull(message = "Debe proporcionar un stock. Este puede ser cero")
    private int stockProducto;

    @NotNull(message = "Debe proporcionar un stock minimo. Este puede ser cero")
    private int stockMinimoProducto;

    @NotNull(message = "Debe proporcionar un proveedor valido")
    @ManyToOne
    @JoinColumn(name = "idProveedorProducto")
    private Proveedor proveedor;

    @NotNull(message = "Debe ingresas una cantidad de unidades a solicitar. Este puede ser cero")
    private int unidadesSolicitarProducto;

    @NotNull(message = "Debe proporcionar una categoria valida")
    @ManyToOne
    @JoinColumn(name = "idCategoriaProducto")
    private Categoria categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "productoCarritoCompra")
    private List<CarritoCompraProducto> carritoCompraProductos;

    //Paso 2: Crear atributo en la clase padre de tipo del enum antes creado en el 
    //paso 1
    //SIGUIENTE PASO: Ver ExpirableProducto.java
    @JsonIgnore
    protected TipoProducto tipoProducto = TipoProducto.NO_EXPIRABLE;


}
