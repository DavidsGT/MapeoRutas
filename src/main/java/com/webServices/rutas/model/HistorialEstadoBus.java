package com.webServices.rutas.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;
import org.springframework.data.couchbase.core.mapping.id.IdSuffix;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;


/**
 * Representa el Historial de los Estados que ha tenido un Bus en un dia
 * @author Davids Adrian Gonzalez Tigrero
 */
@Document
public class HistorialEstadoBus {
	@Id @GeneratedValue(strategy = GenerationStrategy.USE_ATTRIBUTES,delimiter = "::")
	private String id;
	@IdPrefix(order=0)
	private Date fecha;
	@Field
	private Date creadoEn;
	@IdSuffix(order=0)
	private String placaId;
	@Field
	private String placa;
	@Field
	private int linea;
	@Field
	private List<EstadoBus> listaEstados1;
	@Field
	private List<EstadoBus> listaEstados2;
	@Field
	private List<EstadoBus> listaEstados3;
	public HistorialEstadoBus(String placa, List<EstadoBus> listaEstados) {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
		this.fecha = now.getTime();
		this.placa = placa;
		this.placaId = placa;
		this.listaEstados1 = listaEstados;
		this.creadoEn = now.getTime();
	}
	public HistorialEstadoBus() {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        this.fecha = now.getTime();
        this.creadoEn = now.getTime();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
		this.placaId = placa;
	}
	public List<EstadoBus> getListaEstados1() {
		return listaEstados1;
	}
	public void setListaEstados1(List<EstadoBus> listaEstados1) {
		this.listaEstados1 = listaEstados1;
	}
	public List<EstadoBus> getListaEstados2() {
		return listaEstados2;
	}
	public void setListaEstados2(List<EstadoBus> listaEstados2) {
		this.listaEstados2 = listaEstados2;
	}
	public List<EstadoBus> getListaEstados3() {
		return listaEstados3;
	}
	public void setListaEstados3(List<EstadoBus> listaEstados3) {
		this.listaEstados3 = listaEstados3;
	}
	public Date getCreadoEn() {
		return creadoEn;
	}
	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
}