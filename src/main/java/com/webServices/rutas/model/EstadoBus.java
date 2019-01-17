package com.webServices.rutas.model;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.data.geo.Point;

public class EstadoBus {
	private Calendar creationDate;
	private int velocidad;
	private int cantidadUsuarios;
	private Point posicionActual;
	private Boolean estadoPuerta;
	private int linea;
	public EstadoBus(int velocidad, int cantidadUsuarios, Point posicionActual,Boolean estadoPuerta,int linea) {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("ECT"));
		this.velocidad = velocidad;
		this.cantidadUsuarios = cantidadUsuarios;
		this.posicionActual = posicionActual;
		this.estadoPuerta = estadoPuerta;
		this.creationDate = now;
		this.linea = linea;
	}
	public EstadoBus() {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("ECT"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
		now.set(Calendar.HOUR_OF_DAY,0);
		this.creationDate = now;
	}
	public Calendar getCreationDate() {
		return creationDate;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	public Boolean getEstadoPuerta() {
		return estadoPuerta;
	}
	public void setEstadoPuerta(Boolean estadoPuerta) {
		this.estadoPuerta = estadoPuerta;
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
