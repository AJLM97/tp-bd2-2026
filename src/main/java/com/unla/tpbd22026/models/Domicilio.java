package com.unla.tpbd22026.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domicilio {
	private String calle;
	private int numero;
	private String localidad;
	private String provincia;
}
