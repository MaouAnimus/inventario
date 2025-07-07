package com.ecomarket.inventario.assemblers;



import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.ecomarket.inventario.controller.InventarioController;
import com.ecomarket.inventario.model.Producto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler extends RepresentationModelAssemblerSupport<Producto, EntityModel<Producto>>{
    
    public ProductoModelAssembler() {
        super(InventarioController.class, (Class<EntityModel<Producto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(InventarioController.class).getProductoById(producto.getIdProducto(), producto)).withSelfRel(),
                linkTo(methodOn(InventarioController.class).getAll()).withRel("Solicitudes"));
    }

}
