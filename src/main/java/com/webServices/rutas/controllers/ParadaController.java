package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public Iterable<Parada> getAllParada(){
		return paradaService.getAllParada();
	}
	@PostMapping
	public void addParada(@RequestBody Parada parada) {
		paradaService.addParada(parada);
	}
	
	@PutMapping
	public void updateParada(@RequestBody Parada parada) {
		paradaService.addParada(parada);
	}
	
	@GetMapping("/{linea}/radio/{radio}")
	public Iterable<Parada> getParadasCercanasRadio(@RequestBody Point punto,@PathVariable Double radio,@PathVariable int linea) {
		return paradaService.getParadasCercanasRadio(punto,radio,linea);
	}
}