package com.webServices.rutas.model;


import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * Representa una Denuncia.
 * @author Davids Adrian Gonzalez Tigrero
 *
 */
@Document
public class Reporte {
	@IdPrefix
	private String prefix = "denuncia";
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
    private String id;
	
	/**
	 * Identificador de Usuario
	 */
	@Field
	@NotNull
    private String idUsuario;
	
	/**
	 * Asunto de la Denuncia.
	 */
	@Field
	@NotNull
	private String asunto;
	
	/**
	 * Numero de Disco de Bus
	 */
	@Field
	private String numeroDisco;
	
	/**
	 * Ubicación.
	 */
	@Field
	private String ubicacion;
	
	/**
	 * Fecha de creación.
	 */
	@Field
	private Date fecha;
	
	/**
	 * Identificador de Cooperativa.
	 */
	@Field
	@NotNull
	private String idCooperativa;
	
	/**
	 * Descripción del suceso.
	 */
	@Field
	private String mensaje;
	@Field
	private Boolean estado;
	
    public Reporte() {
    	this.fecha = GlobalVariables.getFecha();
    	this.estado = true;
    }

	public Reporte(String id, String idUsuario, String movil, String asunto, String numeroDisco, String ubicacion,
			Date fecha, String idCooperativa, String mensaje) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.asunto = asunto;
		this.numeroDisco = numeroDisco;
		this.ubicacion = ubicacion;
		this.fecha = GlobalVariables.getFecha();
		this.idCooperativa = idCooperativa;
		this.mensaje = mensaje;
		this.estado = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getNumeroDisco() {
		return numeroDisco;
	}

	public void setNumeroDisco(String numeroDisco) {
		this.numeroDisco = numeroDisco;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdCooperativa() {
		return idCooperativa;
	}

	public void setIdCooperativa(String idCooperativa) {
		this.idCooperativa = idCooperativa;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}
