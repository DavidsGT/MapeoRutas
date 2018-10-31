package com.webServices.rutas.model;


import java.util.Date;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.geo.Point;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * 
 * @author Davids Adrian Gonzalez Tigrero
 *
 */
@Document
public class Reporte {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
	@Field
    private String usuario;
	@Field
	private String movil;
	@Field
	private String asunto;
	@Field
	private String numeroDisco;
	@Field
	private Point ubicacion;
	@Field
	private Date fecha;
	@Field
	private String linea;
	@Field
	private String mensaje;

    public Reporte() {
    }

	public Reporte(String id, String usuario, String movil, String asunto, String numeroDisco, Point ubicacion,
			Date fecha, String linea, String mensaje) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.movil = movil;
		this.asunto = asunto;
		this.numeroDisco = numeroDisco;
		this.ubicacion = ubicacion;
		this.fecha = fecha;
		this.linea = linea;
		this.mensaje = mensaje;
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

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
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

	public Point getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Point ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

    
}
