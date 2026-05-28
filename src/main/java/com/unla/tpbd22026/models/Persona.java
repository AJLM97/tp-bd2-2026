package com.unla.tpbd22026.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Persona {
	protected String apellido;
	protected String nombre;
	protected long dni;
	protected Domicilio domicilio;
	protected ObraSocial obraSocial;
}
