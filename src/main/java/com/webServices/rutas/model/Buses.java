package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
@Document
public class Buses {
	@Id
	public String placa;
	@Field
	public int numero;
	@Field
	public int capacidad;
	@Field
	public String idCooperativa;
	@Field
	public String type;
	@Field
	public Boolean estado;
	public Buses() {
		super();
		this.type = "Bus";
	}
	public Buses(String id, String placa, int numero, int capacidad, String cooperativa, String type,Boolean estado) {
		super();
		this.placa = placa;
		this.numero = numero;
		this.capacidad = capacidad;
		this.idCooperativa = cooperativa;
		this.type = "Bus";
		this.estado = estado;
	}
	public String getIdCooperativa() {
		return idCooperativa;
	}
	public void setIdCooperativa(String idCooperativa) {
		this.idCooperativa = idCooperativa;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public String getCooperativa() {
		return idCooperativa;
	}
	public void setCooperativa(String cooperativa) {
		this.idCooperativa = cooperativa;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
