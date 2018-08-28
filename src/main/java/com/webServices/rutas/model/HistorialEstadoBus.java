package com.webServices.rutas.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

public class HistorialEstadoBus {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@CreatedDate
	private Date creationDate;
	@Field
	private String idBus;
	@Field
	private EstadoBus estadoBus;
	@Field
	private String type;
	public HistorialEstadoBus() {
		super();
	}
	public HistorialEstadoBus(String idBus, EstadoBus estadoBus) {
		super();
		this.idBus = idBus;
		this.estadoBus = estadoBus;
		this.type = "historialEstadoBus";
		this.creationDate = new Date();
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getIdBus() {
		return idBus;
	}
	public void setIdBus(String idBus) {
		this.idBus = idBus;
	}
	public EstadoBus getEstadoBus() {
		return estadoBus;
	}
	public void setEstadoBus(EstadoBus estadoBus) {
		this.estadoBus = estadoBus;
	}
}