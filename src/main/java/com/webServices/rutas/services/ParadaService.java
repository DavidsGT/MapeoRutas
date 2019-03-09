package com.webServices.rutas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.repository.ParadaRepository;
import com.webServices.rutas.repository.RutaRepository;

@Service
public class ParadaService {
	@Autowired
	private RutaRepository rutaRepository;
	@Autowired
	private ParadaRepository paradaRepository;
	public Iterable<Parada> getAllParada(){
		return paradaRepository.findByEstadoIsTrue();
	}
	public Iterable<Parada> getAllParadaIgnoreEstado(){
		return paradaRepository.findAll();
	}
	/**
	 * Este metodo agrega nuevas paradas.
	 * @param parada: La parada nueva anadir
	 */
	public Parada addParada(Parada parada) {
		return paradaRepository.save(parada);
	}
	public Parada updateParada(Parada parada) {
		return paradaRepository.save(parada);
	}
	/**
	 * Elimina una Parada
	 * @param placa - Id de la parada a Eliminar
	 */
	public void deleteParada(String id) {
		Parada c = paradaRepository.findById(id).get();
		c.setEstado(false);
		paradaRepository.save(c);
	}
	
	public Iterable<Parada> getParadasCercanasRadio(Point punto,Double longitud,String linea){
		Circle circle = new Circle(punto,new Distance(longitud, Metrics.KILOMETERS));
		List<String> idsParadas = rutaRepository.findById(linea).get().getListasParadas();
		List<Parada> par = (List<Parada>) paradaRepository.findByCoordenadaWithin(circle);
		List<Parada> listOutput = par.stream()
		            .filter(e -> idsParadas.contains(e.getId()))
		            .collect(Collectors.toList());
		return listOutput;
	}
	
	//TODO debe consultar por linea y realizar el query geografico
	public Iterable<Parada> getAllParadaCercanasRadio(Point punto,Double longitud){
		Circle circle = new Circle(punto,new Distance(longitud, Metrics.KILOMETERS));
		return paradaRepository.findByCoordenadaWithinAndIdIn(circle,null);
	}
}