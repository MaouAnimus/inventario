package com.ecomarket.inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")


public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto", unique = true)
    private Long idProducto;

    @Column(nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private String codigoProducto;

}
