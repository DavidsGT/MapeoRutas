package com.webServices.rutas.model;

import org.springframework.data.geo.Point;

public class EstadoBus {
	private int velocidad;
	private int cantidadUsuarios;
	private Point posicionActual;
	public EstadoBus(int velocidad, int cantidadUsuarios, Point posicionActual) {
		super();
		this.velocidad = velocidad;
		this.cantidadUsuarios = cantidadUsuarios;
		this.posicionActual = posicionActual;
	}
	public EstadoBus() {
		super();
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public int getCantidadUsuarios() {
		return cantidadUsuarios;
	}
	public void setCantidadUsuarios(int cantidadUsuarios) {
		this.cantidadUsuarios = cantidadUsuarios;
	}
	public Point getPosicionActual() {
		return posicionActual;
	}
	public void setPosicionActual(Point posicionActual) {
		this.posicionActual = posicionActual;
	}
}
