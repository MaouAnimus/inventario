package com.ecomarket.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.service.InventarioService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/hateoas")
public class InventarioControllerV2 {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping(value = "/listar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listarProductosConLinks() {
        List<Producto> productos = inventarioService.getAll();
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<EntityModel<Producto>> productoModels = productos.stream()
                .map(pro -> EntityModel.of(pro,
                        linkTo(methodOn(InventarioControllerV2.class).buscarProducto(pro.getIdProducto())).withSelfRel()))
                .collect(java.util.stream.Collectors.toList());

        CollectionModel<EntityModel<Producto>> collectionModel = CollectionModel.of(
            productoModels,
            linkTo(methodOn(InventarioControllerV2.class).listarProductosConLinks()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/buscar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> buscarProductos() {
        List<Producto> productos = inventarioService.getAll();
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<EntityModel<Producto>> productoModels = productos.stream()
                .map(pro -> EntityModel.of(pro,
                        linkTo(methodOn(InventarioControllerV2.class).buscarProducto(pro.getIdProducto())).withSelfRel()))
                .collect(java.util.stream.Collectors.toList());

        CollectionModel<EntityModel<Producto>> collectionModel = CollectionModel.of(
            productoModels,
            linkTo(methodOn(InventarioControllerV2.class).buscarProductos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EntityModel<Producto>> buscarProducto(@PathVariable Long id) {
        Producto producto = inventarioService.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Producto> productoModel = EntityModel.of(
            producto,
            linkTo(methodOn(InventarioControllerV2.class).buscarProducto(id)).withSelfRel(),
            linkTo(methodOn(InventarioControllerV2.class).listarProductosConLinks()).withRel("productos")
        );

        return ResponseEntity.ok(productoModel);
    }
}

