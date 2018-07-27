package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Buses;
import com.webServices.rutas.services.BusService;

@RestController
public class BusController {
	@Autowired
	private BusService busService;
	@RequestMapping("/buses")
	public List<Buses> getAllBuses(){
		return busService.getAllBus();
	}
	@RequestMapping("/buses/{id}")
	public Buses getBus(@PathVariable String id) {
		return busService.getBus(id);
	}
	@RequestMapping(method=RequestMethod.POST, value="/buses")
	public void addBus(@RequestBody Buses bus) {
		busService.addBus(bus);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/buses/{id}")
	public void updateBus(@RequestBody Buses bus,@PathVariable String id) {
		busService.updateBus(id, bus);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/buses/{id}")
	public void deleteBus(@PathVariable String id) {
		busService.deleteBus(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/buses")
	public void deleteBuses() {
		busService.deleteAllBus();
	}
}