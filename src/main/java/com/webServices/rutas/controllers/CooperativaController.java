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

import com.webServices.rutas.model.Bus;
import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.services.BusService;
import com.webServices.rutas.services.CooperativaService;

/**
 * Clase que contiene los requestMapping de Cooperativa y los asocia a sus respectivos servicios en {@link CooperativaController}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see CooperativaService
 * @version 1.0
 */
@RestController
@RequestMapping("cooperativas")
public class CooperativaController {
	
	/**
	 * Instancia de los Servicios para Cooperativa
	 */
	@Autowired
	private CooperativaService cooperativaService;
	
	/**
	 * Metodo que Mapea "/cooperativas", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getAllCooperativa()}
	 * y retorna Cooperativas registrados
	 * @return Lista de Cooperativas
	 * @see {@link CooperativaService#getAllCooperativa()}
	 */
	@GetMapping
	public List<Cooperativa> getAllCooperativa(){
		return cooperativaService.getAllCooperativa();
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getAllCooperativaIgnoreEstado()} 
	 * y retorna todos las Cooperativas incluye eliminados logicamente.
	 * @return Lista de Cooperativas incluye eliminados logicamente.
	 * @see {@link CooperativaService#getAllCooperativaIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	public List<Cooperativa> getAllCooperativaIgnoreEstado(){
		return cooperativaService.getAllCooperativaIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/{placa}", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getCooperativa(String)} 
	 * y retorna la Cooperativa
	 * @param id - Id de la cooperativa
	 * @return Cooperativa
	 * @see {@link CooperativaService#getCooperativa(String)} 
	 */
	@GetMapping("/{id}")
	public Cooperativa getCooperativa(@PathVariable String id) {
		return cooperativaService.getCooperativa(id);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/byNombre/{nombre}", RequestMethod es GET, se enlaza al servicio {@link CooperativaService#getCooperativaByNombre(String)} 
	 * y retorna la Cooperativa
	 * @param nombre - nombre de la cooperativa que desee Informacion.
	 * @return Cooperativa
	 * @see {@link CooperativaService#getCooperativaByNombre(String)}
	 */
	@GetMapping("/byNombre/{nombre}")
	public Cooperativa getCooperativaByNombre(@PathVariable String nombre) {
		return cooperativaService.getCooperativaByNombre(nombre);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas", RequestMethod es POST, se enlaza al servicio {@link CooperativaService#addCooperativa(Cooperativa))} 
	 * y retorna Cooperativa registrada
	 * @param cooperativa - Cooperativa a Registrar
	 * @return Cooperativa Registrada
	 * @see {@link CooperativaService#addCooperativa(Cooperativa))}
	 */
	@PostMapping
	public Cooperativa addCooperativa(@RequestBody Cooperativa cooperativa) {
		return cooperativaService.addCooperativa(cooperativa);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas", RequestMethod es PUT, se enlaza al servicio {@link CooperativaService#updateCooperativa(Cooperativa)}.
	 * Actualizar Cooperativa.
	 * @param cooperativa - Cooperativa a Actualizar
	 * @see {@link BusService#updateBus(Bus)}
	 */
	@PutMapping
	public void updateCooperativa(@RequestBody Cooperativa cooperativa) {
		cooperativaService.updateCooperativa(cooperativa);
	}
	
	/**
	 * Metodo que Mapea "/cooperativas/{id}", RequestMethod es DELETE, se enlaza al servicio {@link CooperativaService#deleteCooperativa(String))}.
	 * Eliminar un Bus.
	 * @param id - Id de la Cooperativa
	 * @see {@link CooperativaService#deleteCooperativa(String))}
	 */
	@DeleteMapping("/{id}")
	public void deleteCooperativa(@PathVariable String id) {
		cooperativaService.deleteCooperativa(id);
	}
}
