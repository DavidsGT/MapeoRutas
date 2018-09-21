package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegPerfil;
import com.webServices.rutas.services.SegPerfilService;

@RestController
public class SegPerfilController {
	@Autowired
	private SegPerfilService segPerfilService;
	@RequestMapping("/perfiles")
	public Iterable<SegPerfil> getAllSegPerfil(){
		return segPerfilService.getAllSegPerfil();
	}
	@RequestMapping("/perfiles/{id}")
	public SegPerfil getSegPerfil(@PathVariable String id) {
		return segPerfilService.getSegPerfil(id);
	}
	@RequestMapping(method=RequestMethod.POST, value="/perfiles")
	public void addSegPerfil(@RequestBody SegPerfil segPerfil) {
		segPerfilService.addSegPerfil(segPerfil);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/perfiles/{id}")
	public void updateSegPerfil(@RequestBody SegPerfil segPerfil,@PathVariable String id) {
		segPerfilService.updateSegPerfil(id, segPerfil);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/perfiles/{id}")
	public void deleteSegPerfil(@PathVariable String id) {
		segPerfilService.deleteSegPerfil(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/perfiles")
	public void deleteSegPerfil() {
		segPerfilService.deleteAllSegPerfil();
	}
}
