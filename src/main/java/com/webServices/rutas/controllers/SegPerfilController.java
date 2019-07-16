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
	 * Instancia de los Servicios para {@link SegPerfilService}
	 */
	@Autowired
	private SegPerfilService segPerfilService;
	
	/**
	 * Metodo que Mapea "/perfiles", RequestMethod es GET, se enlaza al servicio {@link SegPerfilService#getAllSegPerfil()} 
	 * y retorna lista de {@link SegPerfil} registrados
	 * @return Lista de {@link SegPerfil}
	 * @see {@link SegPerfilService#getAllSegPerfil()}
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public List<String> getAllSegPerfil(){
		return segPerfilService.getAllSegPerfil();
	}
	
	/**
	 * Metodo que Mapea "/perfiles/create", RequestMethod es POST, se enlaza al servicio {@link SegPerfilService#createPerfil(SegPerfil)} 
	 * @param perfil - Datos de los {@link SegPerfil} a Registrar
	 * @see {@link SegPerfilService#createPerfil(SegPerfil)}
	 */
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void createPerfiles(@RequestBody SegPerfil perfil){
		segPerfilService.createPerfil(perfil);
	}
	
	/**
	 * Metodo que Mapea "/perfiles", RequestMethod es POST, se enlaza al servicio {@link SegPerfilService#addSegPerfil(SegPerfil)} 
	 * y retorna Datos de una {@link SegPerfil} registrado
	 * @param SegPerfil - Datos de la perfil a Registrar
	 * @see {@link SegPerfilService#addSegPerfil(SegPerfil)}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void addSegPerfil(@RequestBody String segPerfil) {
		segPerfilService.addSegPerfil(segPerfil);
	}
	
	/**
	 * Metodo que Mapea "/perfiles/{antes}", RequestMethod es PUT, se enlaza al servicio {@link PerfilService#updatePerfil(String, String)}.
	 * Actualizar asunto.
	 * @param despues - nuevo Perfil
	 * @param antes - Perfil a reemplazar
	 * @see {@link AsuntoService#updateAsunto(String, String)}
	 */
	@PutMapping("/{antes}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void updateSegPerfil(@RequestBody String despues,@PathVariable String antes) {
		segPerfilService.updateSegPerfil(antes,despues);
	}
	
	/**
	 * Metodo que Mapea "/perfiles/{perfil}", RequestMethod es DELETE, se enlaza al servicio {@link SegPerfilService#deleteSegPerfil(String)}.
	 * Eliminar un Perfil.
	 * @param perfil - Perfil a eliminar
	 * @see {@link SegPerfilService#deleteSegPerfil(String)}
	 */
	@DeleteMapping("/{perfil}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteSegPerfil(@PathVariable String perfil) {
		segPerfilService.deleteSegPerfil(perfil);
	}
}
