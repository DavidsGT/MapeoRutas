package com.webServices.rutas.model;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
@Document
public class Buses {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	public String id;
	@Field
	public String placa;
	@Field
	public int numero;
	@Field
	public int capacidad;
	@Field
	public String cooperativa;
	@Field
	public String type;
	public Buses() {
		super();
		this.type = "Bus";
	}
	public Buses(String id, String placa, int numero, int capacidad, String cooperativa, String type) {
		super();
		this.id = id;
		this.placa = placa;
		this.numero = numero;
		this.capacidad = capacidad;
		this.cooperativa = cooperativa;
		this.type = "Bus";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		return cooperativa;
	}
	public void setCooperativa(String cooperativa) {
		this.cooperativa = cooperativa;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
