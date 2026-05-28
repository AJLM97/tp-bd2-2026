package com.unla.tpbd22026.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaProducto {
	private Producto producto;
	private int cantidad;
	private float precioUnitario;
	private float subtotal;
}
