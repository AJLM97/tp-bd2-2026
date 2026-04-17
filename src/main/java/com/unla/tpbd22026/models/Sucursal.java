package com.unla.tpbd22026.models;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
	private int id;
	private Domicilio domicilio;
	private Empleado encargado;
	private List<Empleado> lstEmpleados;
	private long puntoVenta;
	
	public Sucursal(int id, Domicilio domicilio, Empleado encargado, long puntoVenta) {
		this.id = id;
		this.domicilio = domicilio;
		this.encargado = encargado;
		this.lstEmpleados = new ArrayList<Empleado>();
		this.puntoVenta = puntoVenta;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public Empleado getEncargado() {
		return encargado;
	}

	public void setEncargado(Empleado encargado) {
		this.encargado = encargado;
	}

	public List<Empleado> getLstEmpleados() {
		return lstEmpleados;
	}

	public long getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(long puntoVenta) {
		this.puntoVenta = puntoVenta;
	}
	
}
