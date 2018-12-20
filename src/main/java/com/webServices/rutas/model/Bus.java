package com.webServices.rutas.model;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
@Document
public class Bus {
	@Id @GeneratedValue(strategy = GenerationStrategy.USE_ATTRIBUTES)
	private String id;
	@Size(max=7, min=7, message = "Debe contener exactamente 7 Caracteres")
	@Field @IdAttribute
	private String placa;
	@Field
	private int numero;
	@Field
	private int capacidad;
	@Field
	private String idCooperativa;
	@Field
	private String type;
	@Field
	private Boolean estado;
	public Bus() {
		super();
		this.type = "bus";
		this.estado = true;
	}
	public Bus(String id, String placa, int numero, int capacidad, String cooperativa, String type, List<EstadoBus> estadoBus) {
		super();
		this.placa = placa;
		this.numero = numero;
		this.capacidad = capacidad;
		this.idCooperativa = cooperativa;
		this.type = "Bus";
		this.estado = true;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
