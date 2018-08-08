package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.model.Punto;
import com.webServices.rutas.services.GeoSpatialService;

@RestController
public class GeoSpatialController {
	@Autowired
	private GeoSpatialService geoSpatialService;
	@RequestMapping(method=RequestMethod.POST, value="/paradasCercanas")
	public Iterable<Parada> getParadasCercanas(@RequestBody Punto punto) {
		return geoSpatialService.getParadasCercanas(punto);
	}
}