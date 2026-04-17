package com.unla.tpbd22026.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public abstract class Persona {
	@Id
	private String id;

	private String apellido;
	private String nombre;
	private long dni;
	private Domicilio domicilio;
	protected ObraSocial obraSocial;
	
	public Persona(String apellido, String nombre, long dni, Domicilio domicilio, ObraSocial obraSocial) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.domicilio = domicilio;
		this.obraSocial = obraSocial;
	}
}

