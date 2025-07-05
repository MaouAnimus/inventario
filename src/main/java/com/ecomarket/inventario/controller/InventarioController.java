package com.ecomarket.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.service.InventarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping() // Listar todos los productos
    public ResponseEntity<List<Producto>> getAll() {
    List<Producto> inventario = inventarioService.findAll();
    if (inventario.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(inventario, HttpStatus.OK);
    }
    
    @PostMapping("/agregar")
    public ResponseEntity<?> postProducto(@RequestBody Producto producto) {
        try{
            return new ResponseEntity<>(inventarioService.crearProducto(producto), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.CONFLICT)
                         .body("Error al crear solicitud: " + e.getMessage());
        }
    }
    
    @GetMapping("/{idProducto}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long idProducto) {
        Producto producto = inventarioService.findById(idProducto);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }
    
    @PutMapping("/actualizar/{idProducto}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long idProducto, @RequestBody Producto producto) {
        Producto productoUpd = inventarioService.updateProducto(idProducto, producto);
        if (productoUpd != null) {
            return new ResponseEntity<>(productoUpd, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
