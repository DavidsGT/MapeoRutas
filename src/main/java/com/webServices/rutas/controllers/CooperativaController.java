package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.services.CooperativaService;

@RestController
public class CooperativaController {
	@Autowired
	private CooperativaService cooperativaService;
	@RequestMapping("/cooperativasAll")
	public List<Cooperativa> getAllCooperativa(){
		return cooperativaService.getAllCooperativa();
	}
	@RequestMapping("/cooperativas")
	public List<Cooperativa> getAllCooperativaEstadoTrue(){
		return cooperativaService.getAllCooperativaEstadoTrue();
	}
	@RequestMapping("/cooperativas/{id}")
	public Cooperativa getCooperativa(@PathVariable String id) {
		return cooperativaService.getCooperativa(id);
	}
	@RequestMapping("/cooperativasByNombre/{nombre}")
	public Cooperativa getCooperativaByNombre(@PathVariable String nombre) {
		return cooperativaService.getCooperativaByNombre(nombre);
	}
	@RequestMapping(method=RequestMethod.POST, value="/cooperativas")
	public Cooperativa addCooperativa(@RequestBody Cooperativa cooperativa) {
		return cooperativaService.addCooperativa(cooperativa);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/cooperativas")
	public void updateCooperativa(@RequestBody Cooperativa cooperativa) {
		cooperativaService.updateCooperativa(cooperativa);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/cooperativas/{id}")
	public void deleteCooperativa(@PathVariable String id) {
		cooperativaService.deleteCooperativa(id);
	}
}
