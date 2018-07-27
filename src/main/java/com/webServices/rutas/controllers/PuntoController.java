package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Punto;
import com.webServices.rutas.services.PuntoService;

@RestController
public class PuntoController {
	@Autowired
	private PuntoService puntoService;
	@RequestMapping("/puntos")
	public List<Punto> getAllPuntos(){
		return puntoService.getAllPuntos();
	}
	@RequestMapping("/puntos/{id}")
	public Punto getPunto(@PathVariable String id) {
		return puntoService.getPunto(id);
	}
	@RequestMapping(method=RequestMethod.POST, value="/puntos")
	@ResponseBody
	public void addPunto(@RequestBody List<Punto> punto) {
		puntoService.addPunto(punto);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/puntos/{id}")
	public void updatePunto(@RequestBody Punto punto,@PathVariable String id) {
		puntoService.updatePunto(id, punto);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/puntos/{id}")
	public void deletePunto(@PathVariable String id) {
		puntoService.deletePunto(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/puntos")
	public void deletepuntos() {
		puntoService.deleteAllPunto();
	}
}
