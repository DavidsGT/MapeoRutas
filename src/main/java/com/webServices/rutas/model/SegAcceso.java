package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
@Document
public class SegAcceso {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
	private String idSegPerfil;
	@Field
	private String idSegMenu;
	public SegAcceso() {
		super();
	}
	public SegAcceso(String id, String idSegPerfil, String idSegMenu, String type) {
		super();
		this.id = id;
		this.idSegPerfil = idSegPerfil;
		this.idSegMenu = idSegMenu;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdSegPerfil() {
		return idSegPerfil;
	}
	public void setIdSegPerfil(String idSegPerfil) {
		this.idSegPerfil = idSegPerfil;
	}
	public String getIdSegMenu() {
		return idSegMenu;
	}
	public void setIdSegMenu(String idSegMenu) {
		this.idSegMenu = idSegMenu;
	}
	@Override
	public String toString() {
		return "SegAcceso [id=" + id + ", idSegPerfil=" + idSegPerfil + ", idSegMenu=" + idSegMenu + "]";
	}
}
