package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.services.CooperativaService;

/**
 * Clase que contiene los requestMapping de {@link Cooperativa} y los asocia a sus respectivos servicios en {@link CooperativaService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see {@link CooperativaService}
 * @version 1.0
 */
@RestController
@RequestMapping("cooperativas")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CooperativaController {
	
	/**
	 * Instancia de los Servicios para {@link Cooperativa}
	 */
	@Autowired
	private CooperativaService cooperativaService;

	/**
	 * Metodo que Mapea "/cooperativas", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getAllCooperativa()}
	 * y retorna lista de {@link Cooperativa} registrados
	 * @return Lista de {@link Cooperativa}
	 * @see {@link CooperativaService#getAllCooperativa()}
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public List<Cooperativa> getAllCooperativa(){
		return cooperativaService.getAllCooperativa();
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getAllCooperativaIgnoreEstado()} 
	 * y retorna lista de {@link Cooperativa} incluye eliminados logicamente.
	 * @return Lista de {@link Cooperativa} incluye eliminados logicamente.
	 * @see {@link CooperativaService#getAllCooperativaIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public List<Cooperativa> getAllCooperativaIgnoreEstado(){
		return cooperativaService.getAllCooperativaIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/{id}", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getCooperativa(String)} 
	 * y retorna la {@link Cooperativa}
	 * @param id - Id de la {@link Cooperativa}
	 * @return {@link Cooperativa}
	 * @see {@link CooperativaService#getCooperativa(String)} 
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Cooperativa getCooperativa(@PathVariable String id) {
		return cooperativaService.getCooperativa(id);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/byNombre/{nombre}", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getCooperativaByNombre(String)} 
	 * y retorna la {@link Cooperativa}
	 * @param nombre - nombre de la {@link Cooperativa} que desee Informacion.
	 * @return {@link Cooperativa}
	 * @see {@link CooperativaService#getCooperativaByNombre(String)}
	 */
	@GetMapping("/byNombre/{nombre}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Cooperativa getCooperativaByNombre(@PathVariable String nombre) {
		return cooperativaService.getCooperativaByNombre(nombre);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas", RequestMethod es POST, se enlaza al servicio {@link CooperativaService#addCooperativa(Cooperativa))} 
	 * y retorna {@link Cooperativa} registrada
	 * @param cooperativa - {@link Cooperativa} a Registrar
	 * @return {@link Cooperativa} Registrada
	 * @see {@link CooperativaService#addCooperativa(Cooperativa))}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Cooperativa addCooperativa(@RequestBody Cooperativa cooperativa) {
		return cooperativaService.addCooperativa(cooperativa);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas", RequestMethod es PUT, se enlaza al servicio {@link CooperativaService#updateCooperativa(Cooperativa)}.
	 * Actualizar {@link Cooperativa}.
	 * @param cooperativa - {@link Cooperativa} a Actualizar
	 * @see {@link CooperativaService#updateCooperativa(Cooperativa)}
	 */
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Cooperativa updateCooperativa(@RequestBody Cooperativa cooperativa) {
		return cooperativaService.updateCooperativa(cooperativa);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/{id}", RequestMethod es DELETE, se enlaza al servicio {@link CooperativaService#deleteCooperativa(String)}.
	 * Eliminar una {@link Cooperativa}.
	 * @param id - Id de la {@link Cooperativa}
	 * @see {@link CooperativaService#deleteCooperativa(String)}
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(value=HttpStatus.OK, reason="Cooperativa eliminado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void deleteCooperativa(@PathVariable String id) {
		cooperativaService.deleteCooperativa(id);
	}

	/**
	 * Metodo que Mapea "/cooperativas/{id}", RequestMethod es DELETE, se enlaza al servicio {@link CooperativaService#deleteCooperativa(String)}.
	 * Eliminar una {@link Cooperativa}.
	 * @param id - Id de la {@link Cooperativa}
	 * @see {@link CooperativaService#deleteCooperativa(String)}
	 */
	@DeleteMapping("/{id}/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Cooperativa eliminado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteCooperativaPhysical(@PathVariable String id) {
		cooperativaService.deleteCooperativaPhysical(id);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/{id}", RequestMethod es DELETE, se enlaza al servicio {@link CooperativaService#deleteCooperativa(String)}.
	 * Eliminar una {@link Cooperativa}.
	 * @param id - Id de la {@link Cooperativa}
	 * @see {@link CooperativaService#deleteCooperativa(String)}
	 */
	@DeleteMapping("/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Cooperativa eliminado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteAllCooperativaPhysical() {
		cooperativaService.deleteAllCooperativaPhysical();
	}
}