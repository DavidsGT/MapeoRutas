package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.repository.ParadaRepository;

@Service
public class ParadaService {
	@Autowired
	private ParadaRepository paradaRepository;
	/**
	 * Este metodo agrega nuevas paradas.
	 * @param parada: La parada nueva anadir
	 */
	public void addParada(Parada parada) {
		paradaRepository.save(parada);
	}
	public Iterable<Parada> getAllParada(){
		return paradaRepository.findByEstadoIsTrue();
	}
	public Iterable<Parada> getAllParadaIgnoreEstado(){
		return paradaRepository.findAll();
	}
	public Iterable<Parada> getParadasCercanasRadio(Point punto,Double longitud,int linea){
		Circle circle = new Circle(punto,new Distance(longitud, Metrics.KILOMETERS));
		return paradaRepository.findByCoordenadaWithin(circle);
	}
}