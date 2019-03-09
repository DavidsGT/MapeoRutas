package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
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

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.services.ParadaService;

/**
 * Contiene los requestMapping de Paradas y los asocia a sus respectivos servicios en {@link ParadaService}.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 * @see ParadaService
 */
@RestController
@RequestMapping("paradas")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ParadaController {
	
	/**
	 * Instancia de los servicios para Paradas
	 */
	@Autowired
	private ParadaService paradaService;
	
	/**
	 * Metodo que Mapea "/paradas", RequestMethod es GET, se enlaza al servicio {@link ParadaService#getAllParada()} y retorna datos de todos las Paradas registradas
	 * @return Lista de Paradas
	 * @see {@link ParadaService#getAllParada()}
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public Iterable<Parada> getAllParada(){
		return paradaService.getAllParada();
	}
	/**
	 * Metodo que Mapea "/paradas/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link ParadaService#getAllParadaIgnoreEstado())} 
	 * y retorna todos los paradas incluye eliminados logicamente.
	 * @return Paradas incluye eliminados logicamente
	 * @see {@link ParadaService#getAllParadaIgnoreEstado())}
	 */
	@GetMapping("/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public Iterable<Parada> getAllParadaIgnoreEstado(){
		return paradaService.getAllParadaIgnoreEstado();
	}
	
	@GetMapping("/radio/{radio}/point/x={x},y={y}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public Iterable<Parada> getAllParadaIgnoreEstado(@PathVariable double x,@PathVariable double y,@PathVariable Double radio){
		return paradaService.getAllParadaCercanasRadio(new Point(x,y),radio);
	}
	
	/**
	 * Metodo que Mapea "/paradas", RequestMethod es POST, se enlaza al servicio {@link ParadaService#addParada(Parada)} 
	 * y retorna Datos de una parada registrada
	 * @param parada - Datos de la Parada a Registrar
	 * @return Parada Registrado
	 * @see {@link ParadaService#addParada(Parada)}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void addParada(@RequestBody Parada parada) {
		paradaService.addParada(parada);
	}
	
	/**
	 * Metodo que Mapea "/paradas", RequestMethod es PUT, se enlaza al servicio {@link ParadaService#updateParada(Parada)}.
	 * Actualizar Parada.
	 * @param parada - Parada a Actualizar
	 * @return Parada Actualizada
	 * @see {@link ParadaService#updateParada(Parada)}
	 */
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Parada updateParada(@RequestBody Parada parada) {
		return paradaService.updateParada(parada);
	}
	
	/**
	 * Metodo que Mapea "/paradas/{id}", RequestMethod es DELETE, se enlaza al servicio {@link ParadaService#deleteParada(String)}.
	 * Eliminar una Parada.
	 * @param id - Id de la Parada a eliminar
	 * @see {@link ParadaService#deleteParada(String)}
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void deleteAsunto(@PathVariable String id) {
		paradaService.deleteParada(id);
	}
	
	/**
	 * Metodo que Mapea "/{linea}/radio/{radio}/point/{point}", RequestMethod es GET, se enlaza al servicio {@link ParadaService#getParadasCercanasRadio(Point, Double, int)}
	 * @param x - Representa la Latitud
	 * @param y - Representa la Lotitud
	 * @param radio - Representa el radio dado en metros a buscar
	 * @param linea - Representa la Linea del bus a Buscar sus Paradas
	 * @return Lista de paradas cercanas al punto y radio Dado.
	 */
	@GetMapping("/{linea}/radio/{radio}/point/x={x},y={y}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public Iterable<Parada> getParadasCercanasRadio(@PathVariable double x,@PathVariable double y,@PathVariable Double radio,@PathVariable String linea) {
		return paradaService.getParadasCercanasRadio(new Point(x,y),radio,linea);
	}
}