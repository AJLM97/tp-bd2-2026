package com.unla.tpbd22026.services;

import com.unla.tpbd22026.models.Venta;
import com.unla.tpbd22026.models.VentaProducto;
import com.unla.tpbd22026.repositories.VentaRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Venta registrarVenta(Venta venta) {
        float totalVenta = 0.0f;
        if (venta.getItems() != null) {
            for (VentaProducto item : venta.getItems()) {
                float subtotal = item.getPrecioUnitario() * item.getCantidad();
                item.setSubtotal(subtotal);
                totalVenta += subtotal;
            }
        }
        venta.setTotal(totalVenta);
        return repository.save(venta);
    }

    public List<Venta> listarTodas() {
        return repository.findAll();
    }

    public List<Document> obtenerTotalesVentas(Integer puntoVenta, LocalDate desde, LocalDate hasta, String obraSocial, Boolean esPrivado) {
        // Filtro por rango de fechas obligatorio
        Criteria criteria = Criteria.where("fecha").gte(desde).lte(hasta);

        // Filtros dinámicos según los parámetros que envíe el usuario
        if (puntoVenta != null) {
            criteria.and("sucursal.puntoVenta").is(puntoVenta);
        }
        if (esPrivado != null && esPrivado) {
            criteria.and("cliente.obraSocial").is(null);
        } else if (obraSocial != null) {
            criteria.and("cliente.obraSocial.nombre").is(obraSocial);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("sucursal.puntoVenta")
                        .sum("total").as("montoTotalVendido")
                        .count().as("cantidadTicketsEmitidos"),
                Aggregation.project("montoTotalVendido", "cantidadTicketsEmitidos").and("_id").as("puntoVenta")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "ventas", Document.class);
        return results.getMappedResults();
    }

    public List<Document> obtenerVentasPorTipoProducto(Integer puntoVenta, LocalDate desde, LocalDate hasta) {
        Criteria criteria = Criteria.where("fecha").gte(desde).lte(hasta);
        if (puntoVenta != null) {
            criteria.and("sucursal.puntoVenta").is(puntoVenta);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.unwind("items"), // Descompone la lista de ítems para poder evaluar producto por producto
                Aggregation.group("items.producto.tipo")
                        .sum("items.subtotal").as("montoTotalFacturado")
                        .sum("items.cantidad").as("unidadesTotalesVendidas")
        );

        return mongoTemplate.aggregate(aggregation, "ventas", Document.class).getMappedResults();
    }
}
