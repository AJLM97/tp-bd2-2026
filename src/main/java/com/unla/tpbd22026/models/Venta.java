package com.unla.tpbd22026.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ventas")
public class Venta {
	@Id
	private String id;

	private LocalDate fecha;
	private String numeroDeTicket;
	private String formaDePago;
	private float total;

	private Sucursal sucursal;
	private Cliente cliente;
	private Persona empleadoAtendio;
	private Persona empleadoCobrador;

	private List<VentaProducto> items;
}
