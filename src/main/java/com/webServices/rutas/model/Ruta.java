package com.webServices.rutas.model;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.geo.Point;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
/**
 * Representa una Ruta de una Linea con sus respectivas paradas
 * @author Davids
 */
@Document
public class Ruta {
	@Id
	@Field
	private String linea;
	@Field
    private List<Point> listasPuntos;
	@Field
    private List<String> listasParadas;
	@Field
	private String type;
	@Field
	private Boolean estado;
	
    public Ruta() {
    	this.type = "ruta";
    }
    public Ruta(String linea, List<Point> listasPuntos, List<String> listasParadas) {
		super();
		this.linea = linea;
		this.listasPuntos = listasPuntos;
		this.listasParadas = listasParadas;
		this.type = "ruta";
		this.estado = true;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    public List<Point> getListasPuntos() {
        return listasPuntos;
    }
    public void setListasPuntos(List<Point> listasPuntos) {
        this.listasPuntos = listasPuntos;
    }

    public List<String> getListasParadas() {
		return listasParadas;
	}
	public void setListasParadas(List<String> listasParadas) {
		this.listasParadas = listasParadas;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
}