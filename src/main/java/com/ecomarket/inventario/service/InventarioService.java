package com.ecomarket.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.repository.InventarioRepository;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Producto> findAll() {
        return inventarioRepository.findAll();  
    }

    public Producto crearProducto(Producto producto) {

        return inventarioRepository.save(producto);
    }

    public Producto findById(Long idProducto) {
        return inventarioRepository.findById(idProducto).orElse(null);
    }

    public Producto updateProducto(Long idProducto, Producto producto) {
        Producto productoExistente = inventarioRepository.findById(idProducto).orElse(null);
        if (productoExistente != null) {
            if(producto.getNombreProducto() != null) {
                productoExistente.setNombreProducto(producto.getNombreProducto());
            }
            if(producto.getStock() >= 0) {
                productoExistente.setStock(producto.getStock());
            }
            return inventarioRepository.save(productoExistente);
        } else {
            return null; 
        }
    }
    
}
