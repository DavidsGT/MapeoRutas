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
	private String perfil;
	@Field
	private String usuario;
	@Field
	private String clave;
	@Field
	private Boolean estado;
	public SegUsuario() {
		super();
		this.estado = true;
	}
	public SegUsuario(String id, String usuario, String clave, String type, String perfil) {
		super();
		this.id = id;
		this.perfil = perfil;
		this.usuario = usuario;
		this.clave = clave;
		this.estado = true;
	}
	
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String idSegPerfil) {
		this.perfil = idSegPerfil;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String nombre) {
		this.usuario = nombre;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "SegUsuario [id=" + id + ", nombre=" + usuario + ", clave=" + clave + "]";
	}
}
