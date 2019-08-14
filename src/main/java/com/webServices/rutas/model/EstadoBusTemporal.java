package com.webServices.rutas.model;

import java.util.Date;

import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;
import org.springframework.data.geo.Point;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

public class EstadoBusTemporal {
	@IdPrefix
	private String prefix = "estadoBusTemporal";
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
	private String id;
	@Field
	private Date creationDate;
	@Field
	private int velocidad;
	@Field
	private String placa;
	@Field
	private int cantidadUsuarios;
	@Field
	private Point posicionActual;
	@Field
	private Point posicionAnterior;
	@Field
	private Boolean estadoPuerta;
	@Field
	private int idx;
	@Field
	private String linea;
	public EstadoBusTemporal(Date creationDate, int velocidad, int cantidadUsuarios, Point posicionActual,Boolean estadoPuerta,String linea, int idx) {
		super();
		this.velocidad = velocidad;
		this.cantidadUsuarios = cantidadUsuarios;
		this.posicionActual = posicionActual;
		this.estadoPuerta = estadoPuerta;
		this.creationDate = GlobalVariables.getFecha();
		this.linea = linea;
		this.idx = idx;
	}
	public EstadoBusTemporal(EstadoBusTemporal bus) {
		this.velocidad = bus.velocidad;
		this.cantidadUsuarios = bus.cantidadUsuarios;
		this.posicionActual = bus.posicionActual;
		this.estadoPuerta = bus.estadoPuerta;
		this.creationDate = GlobalVariables.getFecha();
		this.linea = bus.linea;
	}
	public EstadoBusTemporal() {
		super();
	}
	
	public EstadoBusTemporal(EstadoBus eb, String linea, String placa) {
		this.cantidadUsuarios = eb.getCantidadUsuarios();
		this.creationDate = GlobalVariables.getFecha();
		this.estadoPuerta = eb.getEstadoPuerta();
		this.linea = linea;
		this.placa = placa;
		this.posicionActual = eb.getPosicionActual();
		this.velocidad = eb.getVelocidad();
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
	public Point getPosicionAnterior() {
		return posicionAnterior;
	}
	public void setPosicionAnterior(Point posicionAnterior) {
		this.posicionAnterior = posicionAnterior;
	}
	public Boolean getEstadoPuerta() {
		return estadoPuerta;
	}
	public void setEstadoPuerta(Boolean estadoPuerta) {
		this.estadoPuerta = estadoPuerta;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	@Override
	public String toString() {
		return "EstadoBusTemporal [id=" + id + ", creationDate=" + creationDate + ", velocidad=" + velocidad
				+ ", cantidadUsuarios=" + cantidadUsuarios + ", posicionActual=" + posicionActual + ", estadoPuerta="
				+ estadoPuerta + ", idx=" + idx + ", linea=" + linea + "]";
	}
	public void updateEstadoBus(EstadoBus eb, String linea, String placa) {
		this.cantidadUsuarios = eb.getCantidadUsuarios();
		this.creationDate =	GlobalVariables.getFecha();
		this.estadoPuerta = eb.getEstadoPuerta();
		this.linea = linea;
		this.placa = placa;
		this.posicionAnterior = this.posicionActual; 
		this.posicionActual = eb.getPosicionActual();
		this.velocidad = eb.getVelocidad();
	}
}