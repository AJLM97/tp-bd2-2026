package com.unla.tpbd22026.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
	private int puntoVenta;
	private Domicilio domicilio;
	private Empleado encargado;
	private List<Empleado> empleados;
}
