package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegPerfil;
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
	
	/**
	 * Metodo que Mapea "/perfiles/{id}", RequestMethod es GET, se enlaza al servicio {@link SegPerfilService#getSegPerfil(String)} 
	 * y retorna el Perfil
	 * @param id - Id del Perfil 
	 * @return Perfil
	 * @see {@link SegPerfilService#getSegPerfil(String)}
	 */
	@GetMapping("/{id}")
	public SegPerfil getSegPerfil(@PathVariable String id) {
		return segPerfilService.getSegPerfil(id);
	}
	
	/**
	 * Metodo que Mapea "/perfiles", RequestMethod es POST, se enlaza al servicio {@link SegPerfilService#addSegPerfil(SegPerfil)} 
	 * y retorna Datos de una Perfil registrado
	 * @param SegPerfil - Datos de la perfil a Registrar
	 * @return Perfil Registrado
	 * @see {@link SegPerfilService#addSegPerfil(SegPerfil)}
	 */
	@PostMapping
	public SegPerfil addSegPerfil(@RequestBody SegPerfil segPerfil) {
		return segPerfilService.addSegPerfil(segPerfil);
	}
	
	/**
	 * Metodo que Mapea "/perfiles", RequestMethod es PUT, se enlaza al servicio {@link SegPerfilService#updateSegPerfil(SegPerfil)}.
	 * Actualizar Perfil.
	 * @param segPerfil - Perfil a Actualizar
	 * @return Perfil Actualizada
	 * @see {@link SegPerfilService#updateSegPerfil(SegPerfil)}
	 */
	@PutMapping
	public SegPerfil updateSegPerfil(@RequestBody SegPerfil segPerfil) {
		return segPerfilService.updateSegPerfil(segPerfil);
	}
	
	/**
	 * Metodo que Mapea "/perfiles/{id}", RequestMethod es DELETE, se enlaza al servicio {@link SegPerfilService#deleteSegPerfil(String)}.
	 * Eliminar un Perfil.
	 * @param id - Id del Perfil
	 * @see {@link SegPerfilService#deleteSegPerfil(String)}
	 */
	@DeleteMapping("/{id}")
	public void deleteSegPerfil(@PathVariable String id) {
		segPerfilService.deleteSegPerfil(id);
	}
}
