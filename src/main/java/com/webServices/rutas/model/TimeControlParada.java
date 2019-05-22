package com.webServices.rutas.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class TimeControlParada {
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private String id;
	@Field
	private String linea;
	@Field
	private List<BetweenParada> listTime = new ArrayList<BetweenParada>();
	
	public TimeControlParada() {
		super();
	}
	public TimeControlParada(String linea) {
		super();
		this.linea = linea;
	}
	public TimeControlParada(String id, String linea, List<BetweenParada> listTime) {
		super();
		this.id = id;
		this.linea = linea;
		this.listTime = listTime;
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
	public List<BetweenParada> getListTime() {
		return listTime;
	}
	public void setListTime(List<BetweenParada> listTime) {
		this.listTime = listTime;
	}
	public void buscarParada1AndParada2(String parada1, String parada2, Long diff) {
		if(this.listTime.isEmpty()) {
			this.listTime.add(new BetweenParada(parada1,parada2,diff));
		}else {
			boolean encontro =false;
			for(int i=0; i< this.listTime.size();i++) {
				if(this.listTime.get(i).getIdparada1() == parada1 && this.listTime.get(i).getIdparada2() == parada2) {
					this.listTime.get(i).addListTiempo(diff);
					encontro =true;
					break;
				}
			}
			if(encontro==false) {
				this.listTime.add(new BetweenParada(parada1,parada2,diff));
			}
		}
	}
	@Override
	public String toString() {
		return "TimeControlParada [id=" + id + ", linea=" + linea + ", listTime=" + listTime + "]";
	}
}
