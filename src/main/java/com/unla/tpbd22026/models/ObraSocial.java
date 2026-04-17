package com.unla.tpbd22026.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ObraSocial {
	private String nombre;
	private int numeroAfiliado;
	
	public ObraSocial(String nombre, int numeroAfiliado) {
		this.nombre = nombre;
		this.numeroAfiliado = numeroAfiliado;
	}
	
}
