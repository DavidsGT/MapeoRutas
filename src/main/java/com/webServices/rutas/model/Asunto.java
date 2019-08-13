package com.webServices.rutas.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class Asunto {
	@Id
	private String id;
	@Field
	private List<String> asuntos = new ArrayList<String>();
	
	public Asunto() {
		super();
		this.id = "Asuntos";
	}
	public Asunto(String id, List<String> asuntos) {
		super();
		this.id = "Asuntos";
		this.asuntos = asuntos;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getAsuntos() {
		return asuntos;
	}
	public void setAsuntos(List<String> asuntos) {
		this.asuntos = asuntos;
	}

}
