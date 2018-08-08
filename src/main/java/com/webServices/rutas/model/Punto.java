package com.webServices.rutas.model;

public class Punto {
	private String type;
	private double latitud;
	private double longitud;
	public Punto() {
		super();
		this.type = "punto";
		// TODO Auto-generated constructor stub
	}
	public Punto(double latitud, double longitud) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
	}
	public Punto(String type, double latitud, double longitud) {
		super();
		this.type = "punto";
		this.latitud = latitud;
		this.longitud = longitud;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	@Override
	public String toString() {
		return "Punto [type=" + type + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}
	
}