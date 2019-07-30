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

import com.webServices.rutas.model.Asunto;
import com.webServices.rutas.services.AsuntoService;

/**
 * Contiene los requestMapping de {@link Asunto} y los asocia a sus respectivos servicios en {@link AsuntoService}.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 * @see AsuntoService
 */
@RestController
@RequestMapping("asuntos")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AsuntoController {
	
	/**
	 * Instancia de los servicios para {@link Asunto}
	 */
	@Autowired
	private AsuntoService asuntoService;
	
	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es GET, se enlaza al servicio {@link AsuntoService#getAllAsunto()} y retorna datos de todos los Asuntos registrados
	 * @return Lista de Asuntos
	 * @see AsuntoService#getAllAsunto()
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL') or hasRole('USER_WEB')")
	public List<String> getAllAsunto(){
		return asuntoService.getAllAsunto();
	}
	
	/**
	 * Metodo que Mapea "/asuntos/create", RequestMethod es POST, se enlaza al servicio {@link AsuntoService#createAsunto(Asunto)} 
	 * @param asunto - Datos del Asunto a Registrar
	 * @see AsuntoService#createAsunto(Asunto)
	 */
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void createAsunto(@RequestBody Asunto asunto) {
		asuntoService.createAsunto(asunto);
	}
	
	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es POST, se enlaza al servicio {@link AsuntoService#addAsunto(String)} 
	 * @param asunto - Datos del Asunto a Registrar
	 * @see AsuntoService#addAsunto(String)
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void addAsunto(@RequestBody String asunto) {
		asuntoService.addAsunto(asunto);
	}

	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es PUT, se enlaza al servicio {@link AsuntoService#updateAsunto(String, String)}.
	 * Actualizar asunto. 
	 * @param despues - nuevo asunto
	 * @param antes - Asunto a reemplazar
	 * @see AsuntoService#updateAsunto(String, String)
	 */
	@PutMapping("/{antes}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void updeteAsunto(@RequestBody String despues,@PathVariable String antes) {
		asuntoService.updateAsunto(antes,despues);
	}

	/**
	 * Metodo que Mapea "/asuntos/{id}", RequestMethod es DELETE, se enlaza al servicio {@link AsuntoService#deleteAsunto(String)}.
	 * Eliminar un Asunto.
	 * @param asunto - Asunto a eliminar
	 * @see AsuntoService#deleteAsunto(String)
	 */
	@DeleteMapping("/{asunto}")
	@PreAuthorize("hasRole('ADMINISTRATOR')  or hasRole('USER_WEB')")
	public void deleteAsunto(@PathVariable String asunto) {
		asuntoService.deleteAsunto(asunto);
	}
}