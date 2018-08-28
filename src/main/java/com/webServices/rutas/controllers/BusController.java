package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Buses;
import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.services.BusService;

@RestController
public class BusController {
	@Autowired
	private BusService busService;
	@RequestMapping("/busesAll")
	public List<Buses> getAllBuses(){
		return busService.getAllBus();
	}
	@RequestMapping("/busesByCooperativa/{idCooperativa}")
	public Iterable<Buses> getAllBusesByCooperativa(@PathVariable String idCooperativa){
		return busService.getBusesByIdCooperativa(idCooperativa);
	}
	@RequestMapping("/buses")
	public List<Buses> getAllBusesEstadoTrue(){
		return busService.getAllBusEstadoTrue();
	}
	@RequestMapping("/buses/{id}")
	public Buses getBus(@PathVariable String id) {
		return busService.getBus(id);
	}
	@RequestMapping(method=RequestMethod.POST, value="/buses")
	public Buses addBus(@RequestBody Buses bus) {
		return busService.addBus(bus);
	}
	@RequestMapping(method=RequestMethod.POST, value="/estadoBus/{id}")
	public void updateEstadoBus(@RequestBody EstadoBus estadoBus,@PathVariable String id) {
		busService.updateEstadoBus(estadoBus,id);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/buses")
	public void updateBus(@RequestBody Buses bus) {
		busService.updateBus(bus);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/buses/{id}")
	public void deleteBus(@PathVariable String id) {
		busService.deleteBus(id);
	}
}