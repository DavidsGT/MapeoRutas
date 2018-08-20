package com.webServices.rutas.model;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
@Document
public class RutaModel {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
	private String numRuta;
	@Field
    private String nombreCooperativa;
	@Field
    private List<Punto> listasPuntos;
	@Field
	private String type;
    public RutaModel() {
    	this.type = "RutaModel";
    }
    public RutaModel(String id, String numRuta, String nombreCooperativa, List<Punto> listasPuntos,
			List<Punto> listasParadas, String type) {
		super();
		this.id = id;
		this.numRuta = numRuta;
		this.nombreCooperativa = nombreCooperativa;
		this.listasPuntos = listasPuntos;
		this.type = "RutaModel";
	}

	public void setNombreCooperativa(String nombreCooperativa) {
		this.nombreCooperativa = nombreCooperativa;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumRuta() {
        return numRuta;
    }

    public void setNumRuta(String numRuta) {
        this.numRuta = numRuta;
    }

    public String getNombreCooperativa() {
        return nombreCooperativa;
    }

    public void setNombreCoopertaiva(String nombreCoopertaiva) {
        this.nombreCooperativa = nombreCoopertaiva;
    }

    public List<Punto> getListasPuntos() {
        return listasPuntos;
    }

    public void setListasPuntos(List<Punto> listasPuntos) {
        this.listasPuntos = listasPuntos;
    }

    @Override
    public String toString() {
        return "RutaModel [numRuta=" + numRuta + ", nombreCoopertaiva=" + nombreCooperativa + ", listasPuntos="
                + listasPuntos + "]";
    }
}