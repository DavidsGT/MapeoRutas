package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class SegUsuario {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
	private String idSegPerfil;
	@Field
	private String nombre;
	@Field
	private String clave;
	public SegUsuario() {
		super();
	}
	public SegUsuario(String id, String nombre, String clave, String type, String idSegPerfil) {
		super();
		this.id = id;
		this.idSegPerfil = idSegPerfil;
		this.nombre = nombre;
		this.clave = clave;
	}
	
	public String getIdSegPerfil() {
		return idSegPerfil;
	}
	public void setIdSegPerfil(String idSegPerfil) {
		this.idSegPerfil = idSegPerfil;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	@Override
	public String toString() {
		return "SegUsuario [id=" + id + ", nombre=" + nombre + ", clave=" + clave + "]";
	}
}
