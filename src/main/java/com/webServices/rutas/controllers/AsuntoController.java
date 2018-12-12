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

import com.webServices.rutas.model.Asunto;
import com.webServices.rutas.services.AsuntoService;

/**
 * Contiene los requestMapping de Asunto y los asocia a sus respectivos servicios en {@link AsuntoService}.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 * @see AsuntoService
 */
@RestController
@RequestMapping("asuntos")
public class AsuntoController {
	
	/**
	 * Instancia de los servicios para Asunto
	 */
	@Autowired
	private AsuntoService asuntoService;
	
	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es GET, se enlaza al servicio {@link AsuntoService#getAllAsunto()} y retorna datos de todos los Asuntos registrados
	 * @return Lista de Asuntos
	 * @see {@link AsuntoService#getAllAsunto()}
	 */
	@GetMapping
	public List<String> getAllAsunto(){
		return asuntoService.getAllAsunto();
	}
	
	/**
	 * Metodo que Mapea "/asuntos/create", RequestMethod es POST, se enlaza al servicio {@link AsuntoService#createAsunto(Asunto)} 
	 * y retorna Datos del Asuntos registrado
	 * @param asunto - Datos del Asunto a Registrar
	 * @see {@link AsuntoService#createAsunto(Asunto)}
	 */
	@PostMapping("/create")
	public void createAsunto(@RequestBody Asunto asunto) {
		asuntoService.createAsunto(asunto);
	}
	
	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es POST, se enlaza al servicio {@link AsuntoService#addAsunto(String)} 
	 * @param asunto - Datos del Asunto a Registrar
	 * @see {@link AsuntoService#addAsunto(String)}
	 */
	@PostMapping
	public void addAsunto(@RequestBody String asunto) {
		asuntoService.addAsunto(asunto);
	}
	
	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es PUT, se enlaza al servicio {@link AsuntoService#updateAsunto(String, String)}.
	 * Actualizar asunto.
	 * @param asunto - nuevo asunto
	 * @param id - Id del asunto a Actualizar
	 * @see {@link AsuntoService#updateAsunto(String, String)}
	 */
	@PutMapping("/{id}")
	public void updeteAsunto(@RequestBody String asunto,@PathVariable int id) {
		asuntoService.updateAsunto(id,asunto);
	}
	
	/**
	 * Metodo que Mapea "/asuntos/{id}", RequestMethod es DELETE, se enlaza al servicio {@link AsuntoService#deleteAsunto(Integer)}.
	 * Eliminar un Asunto.
	 * @param id - Id del asunto a eliminar
	 * @see {@link AsuntoService#deleteAsunto(Integer)}
	 */
	@DeleteMapping("/{id}")
	public void deleteAsunto(@PathVariable int id) {
		asuntoService.deleteAsunto(id);
	}
}
