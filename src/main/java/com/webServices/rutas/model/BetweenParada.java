package com.webServices.rutas.model;

import java.util.List;

public class BetweenParada {
	private String Idparada1;
	private String Idparada2;
	private Long tiempoPromedio;
	private List<Long> listTiempo;
	public String getIdparada1() {
		return Idparada1;
	}
	public void setIdparada1(String idparada1) {
		Idparada1 = idparada1;
	}
	public String getIdparada2() {
		return Idparada2;
	}
	public void setIdparada2(String idparada2) {
		Idparada2 = idparada2;
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
	
}
