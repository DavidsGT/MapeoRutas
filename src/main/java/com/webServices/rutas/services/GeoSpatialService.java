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
import com.webServices.rutas.model.Punto;
import com.webServices.rutas.repository.ParadaRepository;

@Service
public class GeoSpatialService {
	@Autowired
	private ParadaRepository paradaRepository;
	public Iterable<Parada> getParadasCercanas(List<Point> punto){
		//Box cuadro = new Box();
		Box cuadro = new Box(punto.get(0),punto.get(1));
		return paradaRepository.findByCoordenadaWithin(cuadro);
	}
	public Iterable<Parada> getParadasCercanasRadio(Point punto,Double longitud){
		Circle circle = new Circle(punto,new Distance(longitud, Metrics.KILOMETERS));
		return paradaRepository.findByCoordenadaWithin(circle);
	}
}