package com.ecomarket.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomarket.inventario.model.Producto;

@Repository
public interface InventarioRepository extends JpaRepository<Producto, Long> {
}
