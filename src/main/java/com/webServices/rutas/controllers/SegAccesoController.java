package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegAcceso;
import com.webServices.rutas.model.SegMenu;
import com.webServices.rutas.services.SegAccesoService;

@RestController
@RequestMapping("accesos")
public class SegAccesoController {
	@Autowired
	private SegAccesoService segAccesoService;
	@RequestMapping("/accesos")
	public Iterable<SegAcceso> getAllSegAcceso(){
		return segAccesoService.getAllSegAcceso();
	}
	@RequestMapping("/accesos/menu/{usuario}/{clave}")
	public Iterable<SegMenu> getMenus(@PathVariable String usuario,@PathVariable String clave) {
		return segAccesoService.getObtenerMenus(usuario,clave);
	}
	@RequestMapping("/accesos/{id}")
	public SegAcceso getSegAcceso(@PathVariable String id) {
		return segAccesoService.getSegAcceso(id);
	}
	@RequestMapping(method=RequestMethod.POST, value="/accesos")
	public void addSegAcceso(@RequestBody SegAcceso segAcceso) {
		segAccesoService.addSegAcceso(segAcceso);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/accesos/{id}")
	public void updateSegAcceso(@RequestBody SegAcceso segAcceso,@PathVariable String id) {
		segAccesoService.updateSegAcceso(id, segAcceso);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/accesos/{id}")
	public void deleteSegAcceso(@PathVariable String id) {
		segAccesoService.deleteSegAcceso(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/accesos")
	public void deleteSegAcceso() {
		segAccesoService.deleteAllSegAcceso();
	}
}
