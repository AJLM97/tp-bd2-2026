package com.unla.tpbd22026.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
	private String tipo; // "MEDICAMENTO" o "PERFUMERIA"
	private String descripcion;
	private String laboratorio;
	private int codigo;
	private float precio;
}
