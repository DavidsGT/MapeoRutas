package com.webServices.rutas.model;

import java.util.ArrayList;
import java.util.List;

public class BetweenParada {
	
	private String idparada1;
	
	private String idparada2;
	
	private Long tiempoPromedio;
	
	private List<Long> listTiempo = new ArrayList<Long>();
	
	public BetweenParada(String idparada1, String idparada2) {
		super();
		this.idparada1 = idparada1;
		this.idparada2 = idparada2;
		this.tiempoPromedio = new Long(0);
	}
	public String getIdparada1() {
		return idparada1;
	}
	public void setIdparada1(String idparada1) {
		this.idparada1 = idparada1;
	}
	public String getIdparada2() {
		return idparada2;
	}
	public void setIdparada2(String idparada2) {
		this.idparada2 = idparada2;
	}
	public Long getTiempoPromedio() {
		return tiempoPromedio;
	}
	public void setTiempoPromedio(Long tiempoPromedio) {
		this.tiempoPromedio = tiempoPromedio;
	}
	public List<Long> getListTiempo() {
		return listTiempo;
	}
	public void setListTiempo(List<Long> listTiempo) {
		this.listTiempo = listTiempo;
	}
	public void addListTiempo(Long second) {
		Long sumListTiempo = new Long(0);
		List<Long> ltime = getListTiempo();
		ltime.add(second);
		for(Long a : ltime) {
			sumListTiempo = sumListTiempo + a;
		}
		Long promAux = sumListTiempo/ltime.size();
		setTiempoPromedio(promAux);
		setListTiempo(ltime);
	}
	@Override
	public boolean equals(Object obj) {
		BetweenParada ob = (BetweenParada) obj;
		if(ob.idparada1.equals(this.idparada1) && ob.idparada2.equals(this.idparada2)) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public String toString() {
		return "BetweenParada [idparada1=" + idparada1 + ", idparada2=" + idparada2 + "]";
	}
}
