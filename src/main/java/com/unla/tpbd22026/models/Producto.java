package com.unla.tpbd22026.models;

public class Producto {
	private int id;
	private String tipo;
	private String descripcion;
	private String laboratorio;
	private long codigo;
	private double precio;
	
	public Producto(int id, String tipo, String descripcion, String laboratorio, long codigo, double precio) {
		this.id = id;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.laboratorio = laboratorio;
		this.codigo = codigo;
		this.precio = precio;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
