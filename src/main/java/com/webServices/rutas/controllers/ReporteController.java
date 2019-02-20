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


import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.services.ReporteService;

/**
 * Clase que contiene los requestMapping de Reporte y los asocia a sus respectivos servicios en {@link ReporteService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see ReporteService
 * @version 1.0
 */
@RestController
@RequestMapping("reportes")
public class ReporteController {
	
	/**
	 * Instancia de los Servicios para Reporte
	 */
	@Autowired
	private ReporteService reporteService;
	
	/**
	 * Metodo que Mapea "/reportes", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getAllReporte()}
	 * y retorna datos de todos las reportes registradas
	 * @return Lista de Reportes
	 * @see {@link ReporteService#getAllReporte()}
	 */
	@GetMapping
	public Iterable<Reporte> getAllReporte(){
		return reporteService.getAllReporte();
	}
	
	/**
	 * Metodo que Mapea "/reportes/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getAllReporteIgnoreEstado()} 
	 * y retorna todos las reportes incluye eliminados logicamente.
	 * @return Lista de reportes incluye eliminados logicamente.
	 * @see {@link ReporteService#getAllReporteIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	public List<Reporte> getAllReporteIgnoreEstado(){
		return reporteService.getAllReporteIgnoreEstado();
	}

	/**
	 * Metodo que Mapea "/reportes/{id}", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getReporte(String)} 
	 * y retorna el Reporte
	 * @param id - Id del Reporte 
	 * @return Reporte
	 * @see {@link ReporteService#getReporte(String)} 
	 */
	@GetMapping("/{id}")
	public Reporte getReporte(@PathVariable String id){
		return reporteService.getReporte(id);
	}
	
	//TODO HACER REPORTES POR DISCO EN UN RANGO DE FECHA
	//TODO HACER REPORTES DE ASUNTO EN EUN RANGO DE FECHA
	//TODO BUSQUEDA DE REPORTE HECHO SEGUN UN USUARIO
	//TODO HACER REPORTE POR LINEA EN UN RANGO DE FECHA
	//TODO HACER REPORTE POR RANGO DE FECHA
	
	/**
	 * Metodo que Mapea "/reportes", RequestMethod es POST, se enlaza al servicio {@link ReporteService#addReporte(Reporte)} 
	 * y retorna Datos de una reporte registrada
	 * @param reporte - Datos de la reporte a Registrar
	 * @return reporte Registrado
	 * @see {@link ReporteService#addReporte(Reporte)}
	 */
	@PostMapping
	public Reporte addReporte(@RequestBody Reporte reporte) {
		return reporteService.addReporte(reporte);
	}
	
	/**
	 * Metodo que Mapea "/reportes", RequestMethod es PUT, se enlaza al servicio {@link ReporteService#updateReporte(Reporte)}.
	 * Actualizar Reporte.
	 * @param reporte - Reporte a Actualizar
	 * @return Reporte Actualizada
	 * @see {@link ReporteService#updateReporte(Reporte)}
	 */
	@PutMapping
	public Reporte updateReporte(@RequestBody Reporte reporte) {
		return reporteService.updateReporte(reporte);
	}
	
	/**
	 * Metodo que Mapea "/reportes/{id}", RequestMethod es DELETE, se enlaza al servicio {@link ReporteService#deleteReporte(String)}.
	 * Eliminar un Reporte.
	 * @param id - Id del Reporte
	 * @see {@link ReporteService#deleteReporte(String)}
	 */
	@DeleteMapping("/{id}")
	public void deleteReporte(@PathVariable String id) {
		reporteService.deleteReporte(id);
	}
}