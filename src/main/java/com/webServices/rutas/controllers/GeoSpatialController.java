package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.services.GeoSpatialService;

@RestController
public class GeoSpatialController {
	@Autowired
	private GeoSpatialService geoSpatialService;
	@RequestMapping(method=RequestMethod.POST, value="/paradasCercanas")
	public Iterable<Parada> getParadasCercanas(@RequestBody List<Point> punto) {
		return geoSpatialService.getParadasCercanas(punto);
	}
	@RequestMapping(method=RequestMethod.POST, value="/paradasCercanasRadio/{longitud}")
	public Iterable<Parada> getParadasCercanasRadio(@RequestBody Point punto,@PathVariable Double longitud) {
		return geoSpatialService.getParadasCercanasRadio(punto,longitud);
	}
}