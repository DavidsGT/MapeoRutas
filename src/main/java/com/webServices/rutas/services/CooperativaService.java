package com.webServices.rutas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.webServices.rutas.model.Bus;
import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.repository.BusRepository;
import com.webServices.rutas.repository.CooperativaRepository;

/**
 * Contiene los Servicios de {@link Cooperativa} y realiza sus respectivas operaciones.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 */
@Service
public class CooperativaService {
	/**
	 * Instancia para el Repositorio de {@link Cooperativa}
	 * @see {@link CooperativaRepository}
	 */
	@Autowired
	private CooperativaRepository cooperativaRepository;
	
	/**
	 * Instancia para el Repositorio de {@link Bus}
	 * @see {@link BusRepository}
	 */
	@Autowired
	private BusRepository busRepository;
	
	/**
	 * Obtener lista de {@link Cooperativa}
	 * @return Lista de {@link Cooperativa}
	 */
	public List<Cooperativa> getAllCooperativa(){
		return cooperativaRepository.findByEstadoIsTrue()
				.filter(a -> !a.isEmpty())
				.orElseThrow(() ->new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Cooperativas Registrados."));
	}
	
	/**
	 * Obtener datos de una {@link Cooperativa} entregando su respectivo ID.
	 * @param id - ID de la {@link Cooperativa} que desee obtener los datos
	 * @return {@link Cooperativa}
	 */
	public Cooperativa getCooperativa(String id) {
		return cooperativaRepository.findByIdAndEstadoIsTrue(id).orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe la Cooperativa."));
	}
	
	/**
	 * Obtener datos de una {@link Cooperativa} entregando su respectiva ID.
	 * Ignorando su estado eliminado
	 * @param id - ID de la {@link Cooperativa} que desee obtener los datos
	 * @return {@link Cooperativa}
	 */
	public Cooperativa getCooperativaIgnoreEstado(String id) {
		return cooperativaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existe Cooperativa registrado con ID "+id+"."));
	}
	
	/**
	 * Obtener datos de una {@link Cooperativa} entregando su respectiva Nombre.
	 * @param nombre - Nombre de la {@link Cooperativa} que desee obtener los datos
	 * @return {@link Cooperativa}
	 */
	public Cooperativa getCooperativaByNombre(String nombre) {
		return cooperativaRepository.findByNombreAndEstadoIsTrue(nombre).orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe la Cooperativa con el nombre "+nombre+"."));
	}
	
	/**
	 * Obtener Lista de {@link Cooperativa} Ignorando su estado Eliminado
	 * @return Lista de {@link Cooperativa}
	 */
	public List<Cooperativa> getAllCooperativaIgnoreEstado(){
		return Optional.of((List<Cooperativa>) cooperativaRepository.findAll())
				.filter(a -> !a.isEmpty())
				.orElseThrow(() -> new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Cooperativas Registrados."));
	}
	
	/**
	 * Agregar {@link Cooperativa}
	 * @param cooperativa - {@link Cooperativa} que desea guardar
	 * @return {@link Cooperativa} agregado
	 */
	public Cooperativa addCooperativa(Cooperativa cooperativa) {
		if(cooperativaRepository.existsByNombreAndEstadoIsTrue(cooperativa.getNombre()))
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Ya existe Cooperativa cin el Nombre:  "+cooperativa.getNombre()+".");
		else return cooperativaRepository.save(cooperativa);
	}
	/**
	 * Actualiza datos de una {@link Cooperativa}
	 * @param cooperativa - {@link Cooperativa} que desea actualizar sus datos
	 * @return {@link Cooperativa} actualizada
	 */
	public Cooperativa updateCooperativa(Cooperativa cooperativa) {
		if(!cooperativaRepository.existsById(cooperativa.getId()))
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Cooperativa no esta Registrada.");
		return cooperativaRepository.save(cooperativa);
	}
	
	/**
	 * Elimina una {@link Cooperativa}
	 * @param id - ID de la {@link Cooperativa} a Eliminar
	 */
	public void deleteCooperativa(String id) {
		Cooperativa c = getCooperativa(id);
		c.setEstado(false);
		List<Bus> b = busRepository.findByIdCooperativaAndEstadoIsTrue(c.getId()).get();
		b.stream().forEach(t -> t.setIdCooperativa(null));
		busRepository.saveAll(b);
		cooperativaRepository.save(c);
	}
	
	/**
	 * Elimina de manera permanente de la base de Datos una {@link Cooperativa}
	 * @param id - ID del {@link Cooperativa} a eliminar
	 */
	public void deleteCooperativaPhysical(String id) {
		if(cooperativaRepository.existsById(id)) {
			cooperativaRepository.deleteById(id);
			List<Bus> b = busRepository.findByIdCooperativaAndEstadoIsTrue(id).get();
			b.stream().forEach(t -> t.setIdCooperativa(null));
			busRepository.saveAll(b);
		}else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "No existe Cooperativa con el id "+id+".");
		}
	}
	
	/**
	 * Elimina de manera Permanente todas las {@link Cooperativa} registrados en la base de datos.
	 */
	public void deleteAllCooperativaPhysical() {
		busRepository.deleteAll();
		List<Bus> b = (List<Bus>)busRepository.findAll();
		b.stream().forEach(t -> t.setIdCooperativa(null));
		busRepository.saveAll(b);
	}
}
