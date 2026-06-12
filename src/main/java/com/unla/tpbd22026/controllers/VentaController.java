package com.unla.tpbd22026.controllers;

import com.unla.tpbd22026.models.Venta;
import com.unla.tpbd22026.services.VentaService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public Venta crearVenta(@RequestBody Venta venta) {
        return ventaService.registrarVenta(venta);
    }

    @GetMapping("/json-unico")
    public List<Venta> obtenerTodoDesnormalizado() {
        return ventaService.listarTodas();
    }

    // --- REPORTE 1 ---
    @GetMapping("/cantidad-ventas-cadena-sucursal")
    public Document getCantidadVentasCadenaYSucursal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.obtenerCantidadVentasCadenaYSucursal(desde, hasta);
    }

    // --- REPORTE 2 ---
    @GetMapping("/ventas-por-obra-social")
    public List<Document> getVentasPorObraSocial(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.obtenerVentasPorObraSocial(desde, hasta);
    }

    // --- REPORTE 3 ---
    @GetMapping("/cobranza-cadena-sucursal")
    public Document getCobranzaCadenaYSucursal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.obtenerCobranzaCadenaYSucursal(desde, hasta);
    }

    // --- REPORTE 4 ---
    @GetMapping("/ventas-por-tipo-producto")
    public List<Document> getVentasPorTipoProducto(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.obtenerVentasPorTipoProductoerrata(desde, hasta);
    }

    // --- REPORTE 5 ---
    @GetMapping("/ranking-productos-monto-sucursal")
    public Document getRankingProductosMontoSucursal() {
        return ventaService.obtenerRankingProductosPorMontoSucursal();
    }

    // --- REPORTE 6 ---
    @GetMapping("/ranking-productos-cantidad-sucursal")
    public Document getRankingProductosCantidadSucursal() {
        return ventaService.obtenerRankingProductosPorCantidadSucursal();
    }

    // --- REPORTE 7 ---
    @GetMapping("/ranking-clientes-total-cadena")
    public List<Document> getRankingClientesTotalCadena() {
        return ventaService.obtenerRankingClientesTotalCadena();
    }

    // --- REPORTE 8 ---
    @GetMapping("/ranking-clientes-intra-sucursal")
    public Document getRankingClientesIntraSucursal() {
        return ventaService.obtenerRankingClientesIntraSucursal();
    }
}
