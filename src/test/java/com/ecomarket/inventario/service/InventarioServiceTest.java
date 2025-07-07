package com.ecomarket.inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.repository.InventarioRepository;

public class InventarioServiceTest {
    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearProducto() {
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        Producto productoGuardado = new Producto(1L, "Leche 1L", "LECH734", 30);
        
        when(inventarioRepository.save(any(Producto.class))).thenReturn(producto);
        Producto resultado = inventarioService.crearProducto(productoGuardado);
        assertNotNull(resultado);
        assertEquals(productoGuardado.getNombreProducto(), resultado.getNombreProducto());
        assertEquals(productoGuardado.getCodigoProducto(), resultado.getCodigoProducto());
        assertEquals(productoGuardado.getIdProducto(), resultado.getIdProducto());
        assertEquals(productoGuardado.getStock(), resultado.getStock());
        verify(inventarioRepository).save(any(Producto.class));
    }

    @Test
    void testListarProductos() {
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        Producto producto2 = new Producto(1L, "Leche 2L", "LECH735", 35);
        when(inventarioRepository.findAll()).thenReturn(Arrays.asList(producto, producto2));
        List<Producto> resultado = inventarioService.findAll();
        assertThat(resultado).hasSize(2).contains(producto, producto2);
        verify(inventarioRepository).findAll();
    }
    @Test
    void testBuscarProductoPorId() {
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(producto));
        Producto resultado = inventarioService.findById(1L);
        assertThat(resultado).isEqualTo(producto);
        verify(inventarioRepository).findById(1L);
    }

    @Test
    void testUpdateById() {
        Producto producto = new Producto(1L, "Leche 1L", "LECH734", 30);
        Producto productoUpd = new Producto(1L, "Leche 2L", "LECH734", 20);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = inventarioService.updateProducto(1L, productoUpd);

        assertEquals("Leche 2L", resultado.getNombreProducto());
        assertEquals(20, resultado.getStock());
        verify(inventarioRepository).findById(1L);
        verify(inventarioRepository).save(producto);
    }
    @Test
    void testGetAllEmpty() {
        when(inventarioRepository.findAll()).thenReturn(Arrays.asList());
        List<Producto> resultado = inventarioService.getAll();
        assertThat(resultado).isEmpty();
        verify(inventarioRepository).findAll();
    }
    @Test
    void testGetAllNull() {
        when(inventarioRepository.findAll()).thenReturn(null);
        List<Producto> resultado = inventarioService.getAll();
        assertThat(resultado).isEmpty();
        verify(inventarioRepository).findAll();
    }
    @Test
    void testUpdateByIdNotFound() {
        Producto productoUpd = new Producto(1L, "Leche 2L", "LECH734", 20);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());

        Producto resultado = inventarioService.updateProducto(1L, productoUpd);

        assertThat(resultado).isNull();
        verify(inventarioRepository).findById(1L);
        verify(inventarioRepository).findById(1L);
    }
    
}
