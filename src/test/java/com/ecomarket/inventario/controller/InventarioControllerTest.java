package com.ecomarket.inventario.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearProducto() throws Exception {
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        Mockito.when(inventarioService.crearProducto(Mockito.any(Producto.class))).thenReturn(producto);
        
        mockMvc.perform(post("/api/inventario/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreProducto").value("Leche 1L"))
                .andExpect(jsonPath("$.stock").value(30));        
    }
    @Test
    void testListarProductos() throws Exception{
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        Producto producto2 = new Producto(1L, "Leche 2L", "LECH735", 30);
        
        Mockito.when(inventarioService.findAll()).thenReturn(Arrays.asList(producto, producto2));
        mockMvc.perform(get("/api/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreProducto").value("Leche 1L"))
                .andExpect(jsonPath("$[1].nombreProducto").value("Leche 2L"));
    }

    @Test
    void testGetById() throws Exception{
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        Mockito.when(inventarioService.findById(1L)).thenReturn(producto);
        mockMvc.perform(get("/api/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("Leche 1L"))
                .andExpect(jsonPath("$.codigoProducto").value("LECH734"))
                .andExpect(jsonPath("$.stock").value(30));
    }
    @Test
    void testUpdateById() throws Exception{
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        Producto productoUpd = new Producto(1L, "Leche 2L", "LECH734", 35);
        Mockito.when(inventarioService.updateProducto(1L, producto)).thenReturn(productoUpd);
        mockMvc.perform(put("/api/inventario/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("Leche 2L"))
                .andExpect(jsonPath("$.stock").value(35));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        Mockito.when(inventarioService.findById(9999999L)).thenReturn(null);
        mockMvc.perform(get("/api/inventario/9999999"))
                .andExpect(status().isNotFound()); 
    }

    @Test
    void testGetAllNoContent() throws Exception {
        when(inventarioService.findAll()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/api/inventario"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateByIdNotFound() throws Exception{
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        when(inventarioService.updateProducto(1L, producto)).thenReturn(null);
        mockMvc.perform(put("/api/inventario/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAgregarProductoConflict() throws Exception{
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        when(inventarioService.crearProducto(Mockito.any(Producto.class))).thenThrow(new RuntimeException("Error al crear reporte"));
        mockMvc.perform(post("/api/inventario/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isConflict());
    }
}
