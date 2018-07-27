package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
@Document
public class Cooperativa {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	public String id;
	@Field
	private String descripcion;
	@Field
    private String direccion;
	@Field
    private String telefono;
	@Field
	private String email;
	@Field
	public String type;
	public Cooperativa() {
		super();
		this.type = "Cooperativa";
	}
	public Cooperativa(String id, String descripcion, String direccion, String telefono, String email, String type) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.type = "Cooperativa";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setLinea(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
