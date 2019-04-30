package com.webServices.rutas.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.geo.Point;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

public class EstadoBusTemporal {
	
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
	private Date creationDate;
	@Field
	private int velocidad;
	@Field
	private int cantidadUsuarios;
	@Field
	private Point posicionActual;
	@Field
	private Boolean estadoPuerta;
	@Field
	private int linea;
	public EstadoBusTemporal(int velocidad, int cantidadUsuarios, Point posicionActual,Boolean estadoPuerta,int linea) {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
		this.velocidad = velocidad;
		this.cantidadUsuarios = cantidadUsuarios;
		this.posicionActual = posicionActual;
		this.estadoPuerta = estadoPuerta;
		this.creationDate = now.getTime();
		this.linea = linea;
	}
	public EstadoBusTemporal(EstadoBusTemporal bus) {
		this.velocidad = bus.velocidad;
		this.cantidadUsuarios = bus.cantidadUsuarios;
		this.posicionActual = bus.posicionActual;
		this.estadoPuerta = bus.estadoPuerta;
		this.creationDate = bus.creationDate;
		this.linea = bus.linea;
	}
	public EstadoBusTemporal() {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
		this.creationDate = now.getTime();
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	public Boolean getEstadoPuerta() {
		return estadoPuerta;
	}
	public void setEstadoPuerta(Boolean estadoPuerta) {
		this.estadoPuerta = estadoPuerta;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	@Override
	public String toString() {
		return "EstadoBusTemporal [id=" + id + ", creationDate=" + creationDate + ", velocidad=" + velocidad
				+ ", cantidadUsuarios=" + cantidadUsuarios + ", posicionActual=" + posicionActual + ", estadoPuerta="
				+ estadoPuerta + ", linea=" + linea + "]";
	}
	
	
}