package com.unla.tpbd22026.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Document(collection = "empleados")
public class Empleado extends Persona {
	
	private Sucursal sucursal;
	private long cuil;

	public Empleado(String apellido, String nombre, long dni, Domicilio domicilio, ObraSocial obraSocial, Sucursal sucursal, long cuil) {
		super(apellido, nombre, dni, domicilio, obraSocial);
		
		this.sucursal = sucursal;
		this.cuil = cuil;
	}

}
