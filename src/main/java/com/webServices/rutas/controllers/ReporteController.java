package com.webServices.rutas.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.services.ReporteService;

/**
 * Contiene los requestMapping de {@link Reporte} y los asocia a sus respectivos servicios en {@link ReporteService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see ReporteService
 * @version 1.0
 */
@RestController
@RequestMapping("denuncias")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ReporteController {
	
	/**
	 * Instancia de los Servicios para {@link Reporte}
	 */
	@Autowired
	private ReporteService reporteService;
	
	/**
	 * Metodo que Mapea "/reportes", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getAllReporte()}
	 * y retorna lista de {@link Reporte} registradas
	 * @return Lista de {@link Reporte}
	 * @see ReporteService#getAllReporte()
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Iterable<Reporte> getAllReporte(){
		return reporteService.getAllReporte();
	}
	
	/**
	 * Metodo que Mapea "/reportes/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getAllReporteIgnoreEstado()} 
	 * y retorna lista de {@link Reporte} incluye eliminados logicamente.
	 * @return Lista de {@link Reporte} incluye eliminados logicamente.
	 * @see ReporteService#getAllReporteIgnoreEstado()
	 */
	@GetMapping("/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public List<Reporte> getAllReporteIgnoreEstado(){
		return reporteService.getAllReporteIgnoreEstado();
	}

	/**
	 * Metodo que Mapea "/reportes/{id}", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getReporte(String)} 
	 * y retorna el {@link Reporte}
	 * @param id - ID del {@link Reporte} 
	 * @return {@link Reporte}
	 * @see ReporteService#getReporte(String)
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL') or hasRole('USER_WEB')")
	public Reporte getReporte(@PathVariable String id){
		return reporteService.getReporte(id);
	}
	
	/**
	 * Metodo que Mapea "/reportes/{id}/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getReporteIgnoreEstado(String)} 
	 * y retorna la {@link Reporte} ignorando su estado
	 * @param id - Id de la {@link Reporte}
	 * @return {@link Reporte}
	 * @see ReporteService#getReporteIgnoreEstado(String)
	 */
	@GetMapping("/{id}/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public Reporte getReporteIgnoreEstado(@PathVariable String id) {
		return reporteService.getReporteIgnoreEstado(id);
	}
	
	/**
	 * Metodo que Mapea "/reportes/cooperativa/{idCooperativa}/between/{fechaIni}:{fechaFin}", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getReporteByCooperativaAndBetweenDate(String, Date, Date)} 
	 * y retorna lista de {@link Reporte} que se encuentra en un rango de fechas y pertenecen a una {@link Cooperativa}
	 * @param idCooperativa - ID de la {@link Cooperativa}
	 * @param fechaIni - Fecha de Inicio
	 * @param fechaFin - Fecha de Fin
	 * @return Lista de {@link Reporte}
	 * @see ReporteService#getReporteByCooperativaAndBetweenDate(String, Date, Date)
	 */
	@GetMapping("/cooperativa/{idCooperativa}/between/{fechaIni}:{fechaFin}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public List<Reporte> getReporteByCooperativaAndBetweenDate(@PathVariable String idCooperativa,
							@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaIni,
							@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFin) {
		return reporteService.getReporteByCooperativaAndBetweenDate(idCooperativa,fechaIni,fechaFin);
	}
	
	/**
	 * Metodo que Mapea "/reportes/between/{fechaIni}:{fechaFin}", RequestMethod es GET, se enlaza al servicio {@link ReporteService#getReporteBetweenDate(Date, Date)} 
	 * y retorna lista de {@link Reporte} que se encuentra en un rango de fechas
	 * @param fechaIni - Fecha de Inicio
	 * @param fechaFin - Fecha de Fin
	 * @return Lista de {@link Reporte}
	 * @see ReporteService#getReporteBetweenDate(Date, Date)
	 */
	@GetMapping("between/{fechaIni}:{fechaFin}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public List<Reporte> getReporteBetweenDate(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaIni,
							@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFin) {
		return reporteService.getReporteBetweenDate(fechaIni,fechaFin);
	}
	
	/**
	 * Metodo que Mapea "/reportes", RequestMethod es POST, se enlaza al servicio {@link ReporteService#addReporte(Reporte)} 
	 * y retorna Datos de una {@link Reporte} registrada
	 * @param reporte - Datos de la {@link Reporte} a Registrar
	 * @return {@link Reporte} Registrado
	 * @see ReporteService#addReporte(Reporte)
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL')")
	public Reporte addReporte(@RequestBody Reporte reporte) {
		return reporteService.addReporte(reporte);
	}
	
	/**
	 * Metodo que Mapea "/reportes", RequestMethod es PUT, se enlaza al servicio {@link ReporteService#updateReporte(Reporte)}.
	 * Actualizar {@link Reporte}.
	 * @param reporte - {@link Reporte} a Actualizar
	 * @return {@link Reporte} Actualizada
	 * @see ReporteService#updateReporte(Reporte)
	 */
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL')")
	public Reporte updateReporte(@RequestBody Reporte reporte) {
		return reporteService.updateReporte(reporte);
	}
	
	/**
	 * Metodo que Mapea "/reportes/{id}", RequestMethod es DELETE, se enlaza al servicio {@link ReporteService#deleteReporte(String)}.
	 * Eliminar un {@link Reporte}.
	 * @param id - Id del {@link Reporte}
	 * @see ReporteService#deleteReporte(String)
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(value=HttpStatus.OK, reason="Reporte eliminado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL')")
	public void deleteReporte(@PathVariable String id) {
		reporteService.deleteReporte(id);
	}
	
	/**
	 * Metodo que Mapea "/reportes/{id}/physical", RequestMethod es DELETE, se enlaza al servicio {@link ReporteService#deleteReportePhysical(String)}.
	 * Eliminar una {@link Reporte} de la Base de Datos.
	 * @param id - ID de la {@link Reporte}
	 * @see ReporteService#deleteReportePhysical(String)
	 */
	@DeleteMapping("/{id}/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Reporte eliminado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteReportePhysical(@PathVariable String id) {
		reporteService.deleteReportePhysical(id);
	}
	
	/**
	 * Metodo que Mapea "/reportes/physical", RequestMethod es DELETE, se enlaza al servicio {@link ReporteService#deleteAllReportePhysical()}.
	 * Eliminar todos los {@link Reporte} de la Base de Datos.
	 * @see ReporteService#deleteAllReportePhysical()
	 */
	@DeleteMapping("/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Todos los Reportes eliminados con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteAllReportePhysical() {
		reporteService.deleteAllReportePhysical();
	}
	//TODO HACER REPORTES DE ASUNTO EN RANGO DE FECHA
	//TODO BUSQUEDA DE REPORTE HECHO SEGUN UN USUARIO
}