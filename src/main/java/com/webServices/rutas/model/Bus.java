package com.webServices.rutas.model;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	@Size(max=7, min=6, message = "Debe contener de 6 a 7 Caracteres")
	@Field @IdAttribute
	private String placa;
	@Min(value=0,message="El valor minimo es 0")
	@Max(value=300,message="El valor maximo es 300")
	@Field
	private int numeroDisco;
	@Field
	@Min(value=22,message="El valor minimo es 22")
	@Max(value=45,message="El valor maximo es 45")
	private int capacidad;
	@Field
	private String idCooperativa;
	@Field
	private String idEstadoActualTemporal;
	@Field
	private Boolean estado;
	public Bus() {
		super();
		this.estado = true;
	}
	public Bus(String id, String placa, int numeroDisco, int capacidad, String cooperativa, String type, List<EstadoBus> estadoBus) {
		super();
		this.placa = GlobalVariables.confirmPlaca(placa);
		this.numeroDisco = numeroDisco;
		this.capacidad = capacidad;
		this.idCooperativa = cooperativa;
		this.estado = true;
	}
	public String getIdCooperativa() {
		return idCooperativa;
	}
	public void setIdCooperativa(String idCooperativa) {
		this.idCooperativa = idCooperativa;
	}
	
	public String getIdEstadoActualTemporal() {
		return idEstadoActualTemporal;
	}
	public void setIdEstadoActualTemporal(String idEstadoActualTemporal) {
		this.idEstadoActualTemporal = idEstadoActualTemporal;
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
		this.placa = GlobalVariables.confirmPlaca(placa);
	}
	public int getNumeroDisco() {
		return numeroDisco;
	}
	public void setNumeroDisco(int numeroDisco) {
		this.numeroDisco = numeroDisco;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = GlobalVariables.confirmPlaca(id);
	}
	
	@Override
	public String toString() {
		return "Bus [id=" + id + ", placa=" + placa + ", numeroDisco=" + numeroDisco + ", capacidad=" + capacidad
				+ ", idCooperativa=" + idCooperativa + ", estado=" + estado + "]";
	}
}
