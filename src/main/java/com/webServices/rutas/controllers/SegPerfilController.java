package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegPerfil;
import com.webServices.rutas.services.SegAccesoService;
import com.webServices.rutas.services.SegPerfilService;

/**
 * Clase que contiene los requestMapping de Perfiles y los asocia a sus respectivos servicios en {@link SegPerfilService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see SegPerfilService
 * @version 1.0
 */
@RestController
@RequestMapping("perfiles")
public class SegPerfilController {
	/**
	 * Instancia de los Servicios para SegAcceso
	 */
	@Autowired
	private SegPerfilService segPerfilService;
	
	/**
	 * Metodo que Mapea "/perfiles", RequestMethod es GET, se enlaza al servicio {@link SegPerfilService#getAllSegPerfil()}
	 * y retorna datos de todos los perfiles registrados
	 * @return Lista de Perfiles
	 * @see {@link SegPerfilService#getAllSegPerfil()}
	 */
	@GetMapping
	public Iterable<SegPerfil> getAllSegPerfil(){
		return segPerfilService.getAllSegPerfil();
	}
	
	/**
	 * Metodo que Mapea "/perfiles/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link SegPerfilService#getAllSegPerfilIgnoreEstado()} 
	 * y retorna todos los Perfiles incluye eliminados logicamente.
	 * @return Lista de Perfiles incluye eliminados logicamente.
	 * @see {@link SegPerfilService#getAllSegPerfilIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	public Iterable<SegPerfil> getAllSegPerfilIgnoreEstado(){
		return segPerfilService.getAllSegPerfilIgnoreEstado();
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
