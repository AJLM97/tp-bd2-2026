package com.unla.tpbd22026.services;

import com.unla.tpbd22026.models.Venta;
import com.unla.tpbd22026.models.VentaProducto;
import com.unla.tpbd22026.repositories.VentaRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    // --- REPORTE 1 ---
    public Document obtenerCantidadVentasCadenaYSucursal(LocalDate desde, LocalDate hasta) {
        Criteria criteria = Criteria.where("fecha").gte(desde).lte(hasta);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("sucursal.puntoVenta")
                        .count().as("cantidadVentasSucursal"),

                Aggregation.group()
                        .push(new Document("puntoVenta", "$_id")
                                .append("cantidadVentasSucursal", "$cantidadVentasSucursal")
                        ).as("detallePorSucursal")
                        .sum("cantidadVentasSucursal").as("cantidadVentasTotal"),

                Aggregation.project("cantidadVentasTotal", "detallePorSucursal").andExclude("_id")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "ventas", Document.class);
        return results.getMappedResults().isEmpty() ? new Document() : results.getMappedResults().getFirst();
    }

    // --- REPORTE 2 ---
    public List<Document> obtenerVentasPorObraSocial(LocalDate desde, LocalDate hasta) {
        Criteria criteria = Criteria.where("fecha").gte(desde).lte(hasta);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.project()
                        .andExpression("ifNull(cliente.obraSocial.nombre, 'Privado')").as("grupoCobertura"),
                Aggregation.group("grupoCobertura")
                        .count().as("cantidadVentas")
        );

        return mongoTemplate.aggregate(aggregation, "ventas", Document.class).getMappedResults();
    }

    // --- REPORTE 3 ---
    public Document obtenerCobranzaCadenaYSucursal(LocalDate desde, LocalDate hasta) {
        Criteria criteria = Criteria.where("fecha").gte(desde).lte(hasta);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("sucursal.puntoVenta")
                        .sum("total").as("cobranzaSucursal"),

                Aggregation.group()
                        .push(new Document("puntoVenta", "$_id")
                                .append("cobranzaSucursal", "$cobranzaSucursal")
                        ).as("detallePorSucursal")
                        .sum("cobranzaSucursal").as("cobranzaTotal"),

                Aggregation.project("cobranzaTotal", "detallePorSucursal").andExclude("_id")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "ventas", Document.class);
        return results.getMappedResults().isEmpty() ? new Document() : results.getMappedResults().getFirst();
    }

    // --- REPORTE 4 ---
    public List<Document> obtenerVentasPorTipoProductoerrata(LocalDate desde, LocalDate hasta) {
        Criteria criteria = Criteria.where("fecha").gte(desde).lte(hasta);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.unwind("items"),
                Aggregation.group("items.producto.tipo")
                        .count().as("cantidadVentasPorTipo")
        );

        return mongoTemplate.aggregate(aggregation, "ventas", Document.class).getMappedResults();
    }

    // --- REPORTE 5 ---
    public Document obtenerRankingProductosPorMontoSucursal() {
        String jsonUnwind = "{ $unwind: '$items' }";

        String jsonGroupParcial = "{"
                + "  $group: {"
                + "    _id: { puntoVenta: '$sucursal.puntoVenta', codigo: '$items.producto.codigo' },"
                + "    descripcion: { $first: '$items.producto.descripcion' },"
                + "    montoTotal: { $sum: '$items.subtotal' }"
                + "  }"
                + "}";

        String jsonSortMonto = "{ $sort: { montoTotal: -1 } }";

        String jsonGroupFinal = "{"
                + "  $group: {"
                + "    _id: '$_id.puntoVenta',"
                + "    ranking: {"
                + "      $push: {"
                + "        codigo: '$_id.codigo',"
                + "        descripcion: '$descripcion',"
                + "        montoTotal: '$montoTotal'"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        String jsonConsolidarRaiz = "{"
                + "  $group: {"
                + "    _id: null,"
                + "    sucursales: {"
                + "      $push: {"
                + "        puntoVenta: '$_id',"
                + "        ranking: '$ranking'"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        String jsonLimpiarRaiz = "{ $project: { sucursales: 1, _id: 0 } }";

        org.springframework.data.mongodb.core.aggregation.Aggregation aggregation =
                org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation(
                        _ -> Document.parse(jsonUnwind),
                        _ -> Document.parse(jsonGroupParcial),
                        _ -> Document.parse(jsonSortMonto),
                        _ -> Document.parse(jsonGroupFinal),
                        _ -> Document.parse(jsonConsolidarRaiz),
                        _ -> Document.parse(jsonLimpiarRaiz)
                );

        List<Document> results = mongoTemplate.aggregate(aggregation, "ventas", Document.class).getMappedResults();
        return results.isEmpty() ? new Document("sucursales", new ArrayList<>()) : results.getFirst();
    }

    // --- REPORTE 6 ---
    public Document obtenerRankingProductosPorCantidadSucursal() {
        String jsonUnwind = "{ $unwind: '$items' }";

        String jsonGroupParcial = "{"
                + "  $group: {"
                + "    _id: { puntoVenta: '$sucursal.puntoVenta', codigo: '$items.producto.codigo' },"
                + "    descripcion: { $first: '$items.producto.descripcion' },"
                + "    cantidadTotal: { $sum: '$items.cantidad' }"
                + "  }"
                + "}";

        String jsonSortCantidad = "{ $sort: { cantidadTotal: -1 } }";

        String jsonGroupFinal = "{"
                + "  $group: {"
                + "    _id: '$_id.puntoVenta',"
                + "    ranking: {"
                + "      $push: {"
                + "        codigo: '$_id.codigo',"
                + "        descripcion: '$descripcion',"
                + "        cantidadTotal: '$cantidadTotal'"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        String jsonConsolidarRaiz = "{"
                + "  $group: {"
                + "    _id: null,"
                + "    sucursales: {"
                + "      $push: {"
                + "        puntoVenta: '$_id',"
                + "        ranking: '$ranking'"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        String jsonLimpiarRaiz = "{ $project: { sucursales: 1, _id: 0 } }";

        org.springframework.data.mongodb.core.aggregation.Aggregation aggregation =
                org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation(
                        _ -> Document.parse(jsonUnwind),
                        _ -> Document.parse(jsonGroupParcial),
                        _ -> Document.parse(jsonSortCantidad),
                        context -> Document.parse(jsonGroupFinal),
                        context -> Document.parse(jsonConsolidarRaiz),
                        context -> Document.parse(jsonLimpiarRaiz)
                );

        List<Document> results = mongoTemplate.aggregate(aggregation, "ventas", Document.class).getMappedResults();
        return results.isEmpty() ? new Document("sucursales", new ArrayList<>()) : results.getFirst();
    }

    // --- REPORTE 7 ---
    public List<Document> obtenerRankingClientesTotalCadena() {
        String jsonGroup = "{"
                + "  $group: {"
                + "    _id: '$cliente.dni',"
                + "    nombre: { $first: '$cliente.nombre' },"
                + "    apellido: { $first: '$cliente.apellido' },"
                + "    montoTotalComprado: { $sum: '$total' },"
                + "    cantidadVisitasCompras: { $sum: 1 }"
                + "  }"
                + "}";

        String jsonProject = "{"
                + "  $project: {"
                + "    dni: '$_id',"
                + "    nombre: '$nombre',"
                + "    apellido: '$apellido',"
                + "    montoTotalComprado: '$montoTotalComprado',"
                + "    cantidadVisitasCompras: '$cantidadVisitasCompras',"
                + "    _id: 0"
                + "  }"
                + "}";

        String jsonSort = "{ $sort: { montoTotalComprado: -1 } }";

        org.springframework.data.mongodb.core.aggregation.Aggregation aggregation =
                org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation(
                        _ -> Document.parse(jsonGroup),
                        _ -> Document.parse(jsonProject),
                        _ -> Document.parse(jsonSort)
                );

        return mongoTemplate.aggregate(aggregation, "ventas", Document.class).getMappedResults();
    }

    // --- REPORTE 8 ---
    public Document obtenerRankingClientesIntraSucursal() {
        String jsonGroupParcial = "{"
                + "  $group: {"
                + "    _id: { puntoVenta: '$sucursal.puntoVenta', dni: '$cliente.dni' },"
                + "    nombre: { $first: '$cliente.nombre' },"
                + "    apellido: { $first: '$cliente.apellido' },"
                + "    montoTotalComprado: { $sum: '$total' },"
                + "    cantidadVisitasCompras: { $sum: 1 }"
                + "  }"
                + "}";

        String jsonSortMonto = "{ $sort: { montoTotalComprado: -1 } }";

        String jsonGroupFinal = "{"
                + "  $group: {"
                + "    _id: '$_id.puntoVenta',"
                + "    ranking: {"
                + "      $push: {"
                + "        dni: '$_id.dni',"
                + "        nombre: '$nombre',"
                + "        apellido: '$apellido',"
                + "        montoTotalComprado: '$montoTotalComprado',"
                + "        cantidadVisitasCompras: '$cantidadVisitasCompras'"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        String jsonConsolidarRaiz = "{"
                + "  $group: {"
                + "    _id: null,"
                + "    sucursales: {"
                + "      $push: {"
                + "        puntoVenta: '$_id',"
                + "        ranking: '$ranking'"
                + "      }"
                + "    }"
                + "  }"
                + "}";

        String jsonLimpiarRaiz = "{ $project: { sucursales: 1, _id: 0 } }";

        org.springframework.data.mongodb.core.aggregation.Aggregation aggregation =
                org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation(
                        _ -> Document.parse(jsonGroupParcial),
                        _ -> Document.parse(jsonSortMonto),
                        _ -> Document.parse(jsonGroupFinal),
                        _ -> Document.parse(jsonConsolidarRaiz),
                        _ -> Document.parse(jsonLimpiarRaiz)
                );

        List<Document> results = mongoTemplate.aggregate(aggregation, "ventas", Document.class).getMappedResults();
        return results.isEmpty() ? new Document("sucursales", new ArrayList<>()) : results.getFirst();
    }
}
