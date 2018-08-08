package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
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
	public Iterable<Parada> getParadasCercanas(Punto punto){
		Box cuadro = new Box(new Point(-2.2274133,-80.91111661),new Point(-2.2218443,-80.9011143));
		Point point = new Point(Double.valueOf(punto.getLongitud()),Double.valueOf(punto.getLatitud()));
		return paradaRepository.findByCoordenadaWithin(cuadro);
	}
}