package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.geo.Point;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
/**
 * Representa una Parada
 * @author Davids Adrian Gonzalez Tigrero
 */
public class Parada {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
	private String type;
	@Field
	private String nombre;
	@Field
	private String urlFoto;
	@Field
    private Point coordenada;
	@Field
    private Boolean estado;
	public Parada(String nombre, String urlFoto, Point coordenada) {
		super();
		this.type = "parada";
		this.nombre = nombre;
		this.urlFoto = urlFoto;
		this.coordenada = coordenada;
		this.estado = true;
	}
	public Parada() {
		super();
		this.estado = true;
		this.type = "parada";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public Point getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(Point coordenada) {
		this.coordenada = coordenada;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}