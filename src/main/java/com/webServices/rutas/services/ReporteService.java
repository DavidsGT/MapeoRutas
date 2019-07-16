package com.webServices.rutas.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.repository.ReporteRepository;

/**
 * Contiene los Servicios de {@link Reporte} y realiza sus respectivas operaciones.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 */
@Service
public class ReporteService {
	/**
	 * Instancia para el Repositorio de {@link Reporte}
	 * @see {@link ReporteRepository}
	 */
	@Autowired
	private ReporteRepository reporteRepository;
	
	/**
	 * Obtener lista de {@link Reporte}
	 * @return Lista de {@link Reporte}
	 */
	public List<Reporte> getAllReporte(){
		return reporteRepository.findByEstadoIsTrue()
				.filter(a -> !a.isEmpty())
				.orElseThrow(() ->new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Reportes Registrados."));
	}
	
	/**
	 * Obtener datos de una {@link Reporte} entregando su respectivo ID.
	 * @param id - ID de la {@link Reporte} que desee obtener los datos
	 * @return {@link Reporte}
	 */
	public Reporte getReporte(String id){
		return reporteRepository.findByIdAndEstadoIsTrue(id).orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe el Reporte."));
	}
	
	/**
	 * Obtener datos de una {@link Reporte} entregando su respectiva ID.
	 * Ignorando su estado eliminado
	 * @param id - ID de la {@link Reporte} que desee obtener los datos
	 * @return {@link Reporte}
	 */
	public Reporte getReporteIgnoreEstado(String id) {
		return reporteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe Reporte registrado con ID "+id+"."));
	}
	
	/**
	 * Obtener Lista de {@link Reporte} Ignorando su estado Eliminado
	 * @return Lista de {@link Reporte}
	 */
	public List<Reporte> getAllReporteIgnoreEstado() {
		return Optional.of((List<Reporte>) reporteRepository.findAll())
				.filter(a -> !a.isEmpty())
				.orElseThrow(() -> new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Reportes Registrados."));
	}
	
	/**
	 * Agregar {@link Reporte}
	 * @param reporte - {@link Reporte} que desea guardar
	 * @return {@link Reporte} agregado
	 */
	public Reporte addReporte(Reporte reporte) {
		if(reporte.getId()!=null)
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Ya existe Cooperativa con ID:  "+reporte.getId()+".");
		else return reporteRepository.save(reporte);
	}
	
	/**
	 * Actualiza datos de una {@link Reporte}
	 * @param reporte - {@link Reporte} que desea actualizar sus datos
	 * @return {@link Reporte} actualizada
	 */
	public Reporte updateReporte(Reporte reporte) {
		if(!reporteRepository.existsById(reporte.getId()))
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Reporte no esta Registrada.");
		return reporteRepository.save(reporte);
	}
	
	/**
	 * Elimina una {@link Reporte}
	 * @param id - ID de la {@link Reporte} a Eliminar
	 */
	public void deleteReporte(String id) {
		Reporte c = getReporte(id);
		c.setEstado(false);
		reporteRepository.save(c);
	}
	
	
	public List<Reporte> getReporteByCooperativaAndBetweenDate(String idCooperativa, Date fechaIni, Date fechaFin) {
		return reporteRepository.findByIdCooperativaAndEstadoIsTrueAndFechaIsBetween(idCooperativa, fechaIni, fechaFin).get();
	}
	public List<Reporte> getReporteBetweenDate(Date fechaIni, Date fechaFin) {
		return reporteRepository.findByEstadoIsTrueAndFechaIsBetween(fechaIni, fechaFin).get();
	}
	
	/**
	 * Elimina de manera permanente de la base de Datos un {@link Reporte}
	 * @param id - ID del {@link Reporte} a eliminar
	 */
	public void deleteReportePhysical(String id) {
		if(reporteRepository.existsById(id)) {
			reporteRepository.deleteById(id);
		}else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "No existe Reporte con el id "+id+".");
		}
	}
	
	/**
	 * Elimina de manera Permanente todas las {@link Reporte} registrados en la base de datos.
	 */
	public void deleteAllReportePhysical() {
		reporteRepository.deleteAll();
	}
	
	
}
