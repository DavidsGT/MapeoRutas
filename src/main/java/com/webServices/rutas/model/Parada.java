package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

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
    private Punto coordenada;
	public Parada(String nombre, String urlFoto, Punto coordenada) {
		super();
		this.type = "parada";
		this.nombre = nombre;
		this.urlFoto = urlFoto;
		this.coordenada = coordenada;
	}
	public Parada() {
		super();
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
	public Punto getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(Punto coordenada) {
		this.coordenada = coordenada;
	}
	@Override
	public String toString() {
		return "Parada [id=" + id + ", type=" + type + ", nombre=" + nombre + ", urlFoto=" + urlFoto + ", coordenada="
				+ coordenada + "]";
	}
	
}