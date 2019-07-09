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

import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.services.SegUsuarioService;

/**
 * Clase que contiene los requestMapping de Usuario y los asocia a sus respectivos servicios en {@link SegUsuarioService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see SegUsuarioService
 * @version 1.0
 */
@RestController
@RequestMapping("usuarios")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegUsuarioController {
	
	/**
	 * Instancia de los Servicios para Usuario
	 */
	@Autowired
	private SegUsuarioService segUsuarioService;
	/**
	 * Metodo que Mapea "/usuarios", RequestMethod es GET, se enlaza al servicio {@link SegUsuarioService#getAllSegUsuario()}
	 * y retorna datos de todos las Usuarios registrados
	 * @return Lista de Usuarios
	 * @see {@link SegUsuarioService#getAllSegUsuario()}
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public Iterable<SegUsuario> getAllUsuarios(){
		return segUsuarioService.getAllSegUsuario();
	}
	
	/**
	 * Metodo que Mapea "/usuarios/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link SegUsuarioService#getAllSegUsuarioIgnoreEstado()} 
	 * y retorna todos las Usuarios incluye eliminados logicamente.
	 * @return Lista de Usuarios incluye eliminados logicamente.
	 * @see {@link SegUsuarioService#getAllSegUsuarioIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public List<SegUsuario> getAllReporteIgnoreEstado(){
		return segUsuarioService.getAllSegUsuarioIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/usuarios/{id}", RequestMethod es GET, se enlaza al servicio {@link SegUsuarioService#getSegUsuario(String)} 
	 * y retorna el Usuario
	 * @param id - Id del Usuario 
	 * @return Usuario
	 * @see {@link SegUsuarioService#getSegUsuario(String)} 
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public SegUsuario getUsuario(@PathVariable String id) { 
		return segUsuarioService.getSegUsuario(id);
	}
	
	/**
	 * Metodo que Mapea "/usuarios", RequestMethod es POST, se enlaza al servicio {@link SegUsuarioService#addSegUsuario(SegUsuario)} 
	 * y retorna Datos de una Usuario registrado
	 * @param SegUsuario - Datos del Usuario a Registrar
	 * @return Usuario Registrado
	 * @see {@link SegUsuarioService#addSegUsuario(SegUsuario)}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL')")
	public SegUsuario addSegUsuario(@RequestBody SegUsuario segUsuario) {
		return segUsuarioService.addSegUsuario(segUsuario);
	}
	
	/**
	 * Metodo que Mapea "/usuarios", RequestMethod es PUT, se enlaza al servicio {@link SegUsuarioService#updateSegUsuario(SegUsuario)}.
	 * Actualizar Usuario.
	 * @param segUsuario - Usuario a Actualizar
	 * @return Usuario Actualizado
	 * @see {@link SegUsuarioService#updateSegUsuario(SegUsuario)}.
	 */
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL')")
	public SegUsuario updateSegUsuario(@RequestBody SegUsuario segUsuario,@PathVariable String id) {
		return segUsuarioService.updateSegUsuario(segUsuario);
	}
	
	/**
	 * Metodo que Mapea "/usuarios/{id}", RequestMethod es DELETE, se enlaza al servicio {@link SegUsuarioService#deleteSegUsuario(String)}.
	 * Eliminar un Usuario.
	 * @param id - Id del Usuario
	 * @see {@link SegUsuarioService#deleteSegUsuario(String)}.
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK, reason = "Usuario Eliminado Correctamente")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteSegUsuario(@PathVariable String id) {
		segUsuarioService.deleteSegUsuario(id);
	}
	
	@GetMapping("/{usuario}/{clave}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public SegUsuario getUsuarioByNameAndPass(@PathVariable String usuario,@PathVariable String clave) {
		return segUsuarioService.getIdUsuario(usuario,clave);
	}
}