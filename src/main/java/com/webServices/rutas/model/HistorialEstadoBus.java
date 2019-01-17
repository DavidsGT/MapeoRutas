package com.webServices.rutas.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;

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
	@Field @IdAttribute
	private Calendar fecha;
	@Field @IdAttribute
	private String placa;
	@Field
	private List<EstadoBus> ListaEstados;
	public HistorialEstadoBus(String id, Date fecha, String placa, List<EstadoBus> listaEstados) {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("ECT"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, -22);
		this.id = id;
		this.fecha = now ;
		this.placa = placa;
		ListaEstados = listaEstados;
	}
	public HistorialEstadoBus() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public List<EstadoBus> getListaEstados() {
		return ListaEstados;
	}
	public void setListaEstados(List<EstadoBus> listaEstados) {
		ListaEstados = listaEstados;
	}
	
}