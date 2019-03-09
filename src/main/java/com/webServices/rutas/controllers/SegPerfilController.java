package com.webServices.rutas.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegPerfil;
import com.webServices.rutas.services.AsuntoService;
import com.webServices.rutas.services.SegPerfilService;

/**
 * Clase que contiene los requestMapping de Perfiles y los asocia a sus respectivos servicios en {@link SegPerfilService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see SegPerfilService
 * @version 1.0
 */
@RestController
@RequestMapping("perfiles")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegPerfilController {
	/**
	 * Instancia de los Servicios para SegAcceso
	 */
	@Autowired
	private SegPerfilService segPerfilService;
	
	/**
	 * Metodo que Mapea "/perfiles", RequestMethod es GET, se enlaza al servicio {@link SegPerfilService#getAllSegPerfil()} y retorna datos de todos los Perfiles registrados
	 * @return Lista de Perfiles
	 * @see {@link SegPerfilService#getAllSegPerfil()}
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public List<String> getAllSegPerfil(){
		return segPerfilService.getAllSegPerfil();
	}
	
	/**
	 * Metodo que Mapea "/perfiles/create", RequestMethod es POST, se enlaza al servicio {@link SegPerfilService#createPerfil(SegPerfil)} 
	 * @param perfil - Datos de los perfiles a Registrar
	 * @see {@link SegPerfilService#createPerfil(SegPerfil)}
	 */
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void createPerfiles(@RequestBody SegPerfil perfil){
		segPerfilService.createPerfil(perfil);
	}
	
	/**
	 * Metodo que Mapea "/perfiles", RequestMethod es POST, se enlaza al servicio {@link SegPerfilService#addSegPerfil(SegPerfil)} 
	 * y retorna Datos de una Perfil registrado
	 * @param SegPerfil - Datos de la perfil a Registrar
	 * @see {@link SegPerfilService#addSegPerfil(SegPerfil)}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void addSegPerfil(@RequestBody String segPerfil) {
		segPerfilService.addSegPerfil(segPerfil);
	}
	
	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es PUT, se enlaza al servicio {@link AsuntoService#updateAsunto(String, String)}.
	 * Actualizar asunto.
	 * @param despues - nuevo asunto
	 * @param antes - Asunto a reemplazar
	 * @see {@link AsuntoService#updateAsunto(String, String)}
	 */
	@PutMapping("/{antes}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void updateSegPerfil(@RequestBody String despues,@PathVariable String antes) {
		segPerfilService.updateSegPerfil(antes,despues);
	}
	
	/**
	 * Metodo que Mapea "/perfiles/{id}", RequestMethod es DELETE, se enlaza al servicio {@link SegPerfilService#deleteSegPerfil(String)}.
	 * Eliminar un Perfil.
	 * @param id - Id del Perfil
	 * @see {@link SegPerfilService#deleteSegPerfil(String)}
	 */
	@DeleteMapping("/{perfil}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteSegPerfil(@PathVariable String perfil) {
		segPerfilService.deleteSegPerfil(perfil);
	}
}
