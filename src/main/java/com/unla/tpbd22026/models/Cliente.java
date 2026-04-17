package com.unla.tpbd22026.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Document(collection = "clientes")
public class Cliente extends Persona {

	public Cliente(String apellido, String nombre, long dni, Domicilio domicilio, ObraSocial obraSocial) {
		super(apellido, nombre, dni, domicilio, obraSocial);
	}

}
