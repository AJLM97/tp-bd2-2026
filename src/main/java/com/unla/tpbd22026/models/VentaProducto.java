package com.unla.tpbd22026.models;

public class VentaProducto {
	private int id;
	private Producto producto;
	private long cantidad;
	private long precioUnitario;
	
	public VentaProducto(int id, Producto producto, long cantidad, long precioUnitario) {
		super();
		this.id = id;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public long getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(long precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public long getPrecioTotal() {
		return this.precioUnitario * this.cantidad;
	}
	
}
