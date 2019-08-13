package com.webServices.rutas.model;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;
import org.springframework.data.geo.Point;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
/**
 * Representa una Ruta de una Linea con sus respectivas paradas
 * @author Davids
 */
@Document
public class Ruta {
	@IdPrefix
	private String prefix = "ruta";
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
	private String id;
	@Field
	private String linea;
	@Field
    private List<Point> listasPuntos;
	@Field
    private List<String> listasParadas;
	@Field
	private List<String> lugaresInteres;
	@Field
	private Boolean estado; 
	
    public Ruta() {
    	this.estado = true;
    }
    public Ruta(String linea, List<Point> listasPuntos, List<String> listasParadas,List<String> lugaresInteres) {
		super();
		this.linea = linea;
		this.listasPuntos = listasPuntos;
		this.listasParadas = listasParadas;
		this.lugaresInteres = lugaresInteres;
		this.estado = true;
	}
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
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
	public List<String> getLugaresInteres() {
		return lugaresInteres;
	}
	public void setLugaresInteres(List<String> lugaresInteres) {
		this.lugaresInteres = lugaresInteres;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}