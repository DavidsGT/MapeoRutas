package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * @return {@link Asunto}
	 * @see AsuntoService#createAsunto(Asunto)
	 */
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public Asunto createAsunto(@RequestBody Asunto asunto) {
		return asuntoService.createAsunto(asunto);
	}
	
	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es POST, se enlaza al servicio {@link AsuntoService#addAsunto(String)} 
	 * @param nuevo - Datos del Asunto a Registrar
	 * @see AsuntoService#addAsunto(String)
	 * @return {@link Asunto}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Asunto addAsunto(@RequestParam("nuevo") String nuevo) {
		return asuntoService.addAsunto(nuevo);
	}

	/**
	 * Metodo que Mapea "/asuntos", RequestMethod es PUT, se enlaza al servicio {@link AsuntoService#updateAsunto(String, String)}.
	 * Actualizar asunto. 
	 * @param despues - nuevo asunto
	 * @param antes - Asunto a reemplazar
	 * @return {@link Asunto}
	 * @see AsuntoService#updateAsunto(String, String)
	 */
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Asunto updeteAsunto(@RequestParam("anterior") String antes,@RequestParam("nuevo") String despues) {
		return asuntoService.updateAsunto(antes,despues);
	}

	/**
	 * Metodo que Mapea "/asuntos/{id}", RequestMethod es DELETE, se enlaza al servicio {@link AsuntoService#deleteAsunto(String)}.
	 * Eliminar un Asunto.
	 * @param asunto - Asunto a eliminar
	 * @return {@link Asunto}
	 * @see AsuntoService#deleteAsunto(String)
	 */
	@DeleteMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')  or hasRole('USER_WEB')")
	public Asunto deleteAsunto(@RequestParam("eliminar") String asunto) {
		return asuntoService.deleteAsunto(asunto);
	}
}