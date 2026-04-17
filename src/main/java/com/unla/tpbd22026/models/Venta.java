package com.unla.tpbd22026.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Venta {
	private int id;
	private LocalDate fecha;
	private long numeroTicket;
	private Sucursal sucursal;
	private Cliente cliente;
	private Empleado empleadoAtendio;
	private Empleado empleadoCajero;
	private String formaDePago;
	private List<VentaProducto> lstProductosVendidos;
	
	public Venta(int id, LocalDate fecha, long numeroTicket, Sucursal sucursal, Cliente cliente,
			Empleado empleadoAtendio, Empleado empleadoCajero, String formaDePago, List<VentaProducto> lstProductosVendidos,
			double total) {
		this.id = id;
		this.fecha = fecha;
		this.numeroTicket = numeroTicket;
		this.sucursal = sucursal;
		this.cliente = cliente;
		this.empleadoAtendio = empleadoAtendio;
		this.empleadoCajero = empleadoCajero;
		this.formaDePago = formaDePago;
		this.lstProductosVendidos = new ArrayList<VentaProducto>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public long getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(long numeroTicket) {
		this.numeroTicket = numeroTicket;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empleado getEmpleadoAtendio() {
		return empleadoAtendio;
	}

	public void setEmpleadoAtendio(Empleado empleadoAtendio) {
		this.empleadoAtendio = empleadoAtendio;
	}

	public Empleado getEmpleadoCajero() {
		return empleadoCajero;
	}

	public void setEmpleadoCajero(Empleado empleadoCajero) {
		this.empleadoCajero = empleadoCajero;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	public double getTotal() {
		double total = 0;

		Iterator<VentaProducto> iterador = lstProductosVendidos.iterator();

		while(iterador.hasNext()) {
			VentaProducto item = iterador.next();
			total += item.getPrecioTotal();
		}

		return total;
	}

	public List<VentaProducto> getLstProductosVendidos() {
		return lstProductosVendidos;
	}
	
}
