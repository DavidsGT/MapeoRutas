package com.webServices.rutas.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class Asunto {
	@Id
	private String id;
	@Field
	private Map<String,String> asuntos = new HashMap<String,String>();
	
	public Asunto() {
		super();
	}
	public Asunto(String id, Map<String,String> asuntos) {
		super();
		this.id = id;
		this.asuntos = asuntos;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String,String> getAsuntos() {
		return asuntos;
	}
	public void setAsuntos(Map<String,String> asuntos) {
		this.asuntos = asuntos;
	}

}
