package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
@Document
public class Cooperativa {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
	private String nombre;
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
	@Field
	public Boolean estado;
	public Cooperativa() {
		super();
		this.type = "Cooperativa";
		this.estado = true;
	}
	public Cooperativa(String id, String nombre, String descripcion, String direccion, String telefono, String email,
			String type, Boolean estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.type = "Cooperativa";
		this.estado = true;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
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
