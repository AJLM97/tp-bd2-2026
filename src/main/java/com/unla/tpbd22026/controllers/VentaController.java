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

    @GetMapping("/reporte-totales")
    public List<Document> getVentasTotales(
            @RequestParam(required = false) Integer puntoVenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) String obraSocial,
            @RequestParam(required = false) Boolean esPrivado) {
        return ventaService.obtenerTotalesVentas(puntoVenta, desde, hasta, obraSocial, esPrivado);
    }

    @GetMapping("/reporte-por-tipo")
    public List<Document> getVentasPorTipoProducto(
            @RequestParam(required = false) Integer puntoVenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ventaService.obtenerVentasPorTipoProducto(puntoVenta, desde, hasta);
    }
}
