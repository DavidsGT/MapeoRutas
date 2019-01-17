package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegAcceso;
import com.webServices.rutas.model.SegMenu;
import com.webServices.rutas.services.SegAccesoService;

/**
 * Clase que contiene los requestMapping de Accesos y los asocia a sus respectivos servicios en {@link SegAccesoService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see SegAccesoService
 * @version 1.0
 */
@RestController
@RequestMapping("accesos")
public class SegAccesoController {
	/**
	 * Instancia de los Servicios para SegAcceso
	 */
	@Autowired
	private SegAccesoService segAccesoService;
	
	/**
	 * Metodo que Mapea "/accesos", RequestMethod es GET, se enlaza al servicio {@link SegAccesoService#getAllSegAcceso()}
	 * y retorna datos de todos los Accesos registrados
	 * @return Lista de Accesos
	 * @see {SegAccesoService#getAllSegAcceso()}
	 */
	@GetMapping
	public Iterable<SegAcceso> getAllSegAcceso(){
		return segAccesoService.getAllSegAcceso();
	}
	
	/**
	 * Metodo que Mapea "/accesos/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link SegAccesoService#getAllSegAccesoIgnoreEstado()} 
	 * y retorna todos los Accesos incluye eliminados logicamente.
	 * @return Lista de Accesos incluye eliminados logicamente.
	 * @see {@link SegAccesoService#getAllSegAccesoIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	public List<SegAcceso> getAllSegAccesoIgnoreEstado(){
		return segAccesoService.getAllSegAccesoIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/accesos/{id}", RequestMethod es GET, se enlaza al servicio {@link SegAccesoService#getSegAcceso(String)} 
	 * y retorna el Acceso
	 * @param id - Id del Acceso 
	 * @return Acceso
	 * @see {@link SegAccesoService#getSegAcceso(String)}
	 */
	@GetMapping("/{id}")
	public SegAcceso getSegAcceso(@PathVariable String id) {
		return segAccesoService.getSegAcceso(id);
	}
	
	/**
	 * Metodo que Mapea "/accesos", RequestMethod es POST, se enlaza al servicio {@link SegAccesoService#addSegAcceso(SegAcceso)} 
	 * y retorna Datos de una Acceso registrada
	 * @param acceso - Datos de la acceso a Registrar
	 * @return acceso Registrado
	 * @see {@link SegAccesoService#addSegAcceso(SegAcceso)}
	 */
	@PostMapping
	public SegAcceso addSegAcceso(@RequestBody SegAcceso segAcceso) {
		return segAccesoService.addSegAcceso(segAcceso);
	}
	
	/**
	 * Metodo que Mapea "/acceso", RequestMethod es PUT, se enlaza al servicio {@link SegAccesoService#updateSegAcceso(SegAcceso)}.
	 * Actualizar Acceso.
	 * @param segAcceso - Acceso a Actualizar
	 * @return Acceso Actualizada
	 * @see {@link SegAccesoService#updateSegAcceso(SegAcceso)}
	 */
	@PutMapping
	public SegAcceso updateSegAcceso(@RequestBody SegAcceso segAcceso) {
		return segAccesoService.updateSegAcceso(segAcceso);
	}
	
	/**
	 * Metodo que Mapea "/accesos/{id}", RequestMethod es DELETE, se enlaza al servicio {@link SegAccesoService#deleteSegAcceso(String)}.
	 * Eliminar un Acceso.
	 * @param id - Id del Acceso
	 * @see {@link SegAccesoService#deleteSegAcceso(String)}
	 */
	@DeleteMapping("/{id}")
	public void deleteSegAcceso(@PathVariable String id) {
		segAccesoService.deleteSegAcceso(id);
	}
	/**
	 * Metodo que Mapea "/accesos/us:{usuario}&cl:{clave}/menu", RequestMethod es DELETE, se enlaza al servicio {@link SegAccesoService#getObtenerMenus(String, String)}.
	 * @param usuario - Nombre de Usuario
	 * @param clave - Clave del Usuario
	 * @return El menu a la que el usuario tiene acceso
	 */
	@GetMapping("/us:{usuario}&cl:{clave}/menu")
	public Iterable<SegMenu> getMenus(@PathVariable String usuario,@PathVariable String clave) {
		return segAccesoService.getObtenerMenus(usuario,clave);
	}
}
