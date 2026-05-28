package com.unla.tpbd22026.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Empleado extends Persona {
	private long cuit;
	private String idSucursal;
}
