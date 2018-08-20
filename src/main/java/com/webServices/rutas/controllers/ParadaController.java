package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.services.ParadaService;

@RestController
public class ParadaController {
	@Autowired
	private ParadaService paradaService;
	@RequestMapping("/paradas")
	public Iterable<Parada> getAllParada(){
		return paradaService.getAllParada();
	}
	@RequestMapping(method=RequestMethod.POST, value="/parada")
	public void addParada(@RequestBody Parada parada) {
		paradaService.addParada(parada);
	}
}