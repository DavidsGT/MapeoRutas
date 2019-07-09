package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
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

import com.webServices.rutas.model.Bus;
import com.webServices.rutas.model.Parada;
import com.webServices.rutas.services.ParadaService;

/**
 * Contiene los requestMapping de {@link Parada} y los asocia a sus respectivos servicios en {@link ParadaService}.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 * @see {@link ParadaService}
 */
@RestController
@RequestMapping("paradas")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ParadaController {
	
	/**
	 * Instancia de los servicios para {@link Parada}
	 */
	@Autowired
	private ParadaService paradaService;
	
	/**
	 * Metodo que Mapea "/paradas", RequestMethod es GET, se enlaza al servicio {@link ParadaService#getAllParada()} y retorna datos de todos las {@link Parada} registradas
	 * @return Lista de {@link Parada}
	 * @see {@link ParadaService#getAllParada()}
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public Iterable<Parada> getAllParada(){
		return paradaService.getAllParada();
	}
	
	/**
	 * Metodo que Mapea "/paradas/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link ParadaService#getAllParadaIgnoreEstado()} 
	 * y retorna todos los {@link Parada} incluye eliminados logicamente.
	 * @return Lista de {@link Parada} incluye eliminados logicamente
	 * @see {@link ParadaService#getAllParadaIgnoreEstado())}
	 */
	@GetMapping("/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public Iterable<Parada> getAllParadaIgnoreEstado(){
		return paradaService.getAllParadaIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/paradas/radio/{radio}/point/x={x},y={y}", RequestMethod es GET, se enlaza al servicio {@link ParadaService#getAllParadaCercanasRadio(Point, Double)} 
	 * y retorna todos una Lista de {@link Parada} cercanas al radio dado.
	 * @param x - Representa la Longitud
	 * @param y - Representa la Latitud
	 * @param radio - Representa el Radio de distancia dado en Kilometros a Mapear
	 * @return - Lista de {@link Parada} ceranas.
	 */
	@GetMapping("/radio/{radio}/point/x={x},y={y}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public Iterable<Parada> getAllParadaIgnoreEstado(@PathVariable double x,@PathVariable double y,@PathVariable Double radio){
		return paradaService.getAllParadaCercanasRadio(new Point(x,y),radio);
	}
	
	/**
	 * Metodo que Mapea "/paradas", RequestMethod es POST, se enlaza al servicio {@link ParadaService#addParada(Parada)} 
	 * y retorna Datos de una {@link Parada} registrada
	 * @param parada - Datos de la {@link Parada} a Registrar
	 * @return {@link Parada} Registrado
	 * @see {@link ParadaService#addParada(Parada)}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void addParada(@RequestBody Parada parada) {
		paradaService.addParada(parada);
	}
	
	/**
	 * Metodo que Mapea "/paradas", RequestMethod es PUT, se enlaza al servicio {@link ParadaService#updateParada(Parada)}.
	 * Actualizar {@link Parada}.
	 * @param parada - {@link Parada} a Actualizar
	 * @return {@link Parada} Actualizada
	 * @see {@link ParadaService#updateParada(Parada)}
	 */
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Parada updateParada(@RequestBody Parada parada) {
		return paradaService.updateParada(parada);
	}
	
	/**
	 * Metodo que Mapea "/paradas/{id}", RequestMethod es DELETE, se enlaza al servicio {@link ParadaService#deleteParada(String)}.
	 * Eliminar una {@link Parada}.
	 * @param id - ID de la {@link Parada} a eliminar
	 * @see {@link ParadaService#deleteParada(String)}
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(value=HttpStatus.OK, reason="Parada eliminado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void deleteParada(@PathVariable String id) {
		paradaService.deleteParada(id);
	}
	
	/**
	 * Metodo que Mapea "/paradas/{linea}/radio/{radio}/point/{point}", RequestMethod es GET, se enlaza al servicio {@link ParadaService#getParadasCercanasRadio(Point, Double, int)}
	 * y retorna una lista de {@link Parada} que estan en el radio dado.
	 * @param x - Representa la Latitud
	 * @param y - Representa la Lotitud
	 * @param radio - Representa el radio dado en Kilometros a buscar
	 * @param linea - Representa la Linea del {@link Bus} a Buscar sus {@link Parada}
	 * @return Lista de {@link Parada} cercanas al punto y radio Dado.
	 */
	@GetMapping("/{linea}/radio/{radio}/point/x={x},y={y}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public Iterable<Parada> getParadasCercanasRadio(@PathVariable double x,@PathVariable double y,@PathVariable Double radio,@PathVariable String linea) {
		return paradaService.getParadasCercanasRadio(new Point(x,y),radio,linea);
	}
	
	/**
	 * Metodo que Mapea "/paradas/{id}", RequestMethod es DELETE, se enlaza al servicio {@link ParadaService#deleteParadaPhysical(String)}.
	 * Eliminar una {@link Parada}.
	 * @param id - Id de la {@link Parada}
	 * @see {@link ParadaService#deleteParadaPhysical(String)}
	 */
	@DeleteMapping("/{id}/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Parada eliminada de la Base de Datos con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteParadaPhysical(@PathVariable String id) {
		paradaService.deleteParadaPhysical(id);
	}
	
	/**
	 * Metodo que Mapea "/paradas/physical", RequestMethod es DELETE, se enlaza al servicio {@link ParadaService#deleteAllParadaPhysical()}.
	 * Eliminar una {@link Parada}.
	 * @param id - Id de la {@link Parada}
	 * @see {@link ParadaService#deleteAllParadaPhysical()}
	 */
	@DeleteMapping("/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Paradas eliminadas de la Base de Datos con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteAllParadaPhysical() {
		paradaService.deleteAllParadaPhysical();
	}
}