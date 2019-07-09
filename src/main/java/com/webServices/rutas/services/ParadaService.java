package com.webServices.rutas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.webServices.rutas.model.GlobalVariables;
import com.webServices.rutas.model.Parada;
import com.webServices.rutas.repository.ParadaRepository;
import com.webServices.rutas.repository.RutaRepository;

/**
 * Contiene los Servicios de {@link Parada} y realiza sus respectivas operaciones.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 */
@Service
public class ParadaService {
	/**
	 * Instancia para el Repositorio de {@link Ruta}
	 * @see {@link RutaRepository}
	 */
	@Autowired
	private RutaRepository rutaRepository;
	
	/**
	 * Instancia para el Repositorio de {@link Parada}
	 * @see {@link ParadaRepository}
	 */
	@Autowired
	private ParadaRepository paradaRepository;
	
	/**
	 * Obtener lista de {@link Parada}
	 * @return Lista de {@link Parada}
	 */
	public Iterable<Parada> getAllParada(){
		return paradaRepository.findByEstadoIsTrue()
				.filter(a -> !a.isEmpty())
				.orElseThrow(() -> new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Paradas Registrados."));
	}
	
	/**
	 * Obtener Lista de {@link Parada} Ignorando su estado Eliminado
	 * @return Lista de {@link Parada}
	 */
	public Iterable<Parada> getAllParadaIgnoreEstado(){
		return Optional.of((List<Parada>)paradaRepository.findAll())
				.filter(a -> !a.isEmpty())
				.orElseThrow(() -> new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Paradas Registrados."));
	}
	
	/**
	 * Agregar {@link Parada}
	 * @param parada - {@link Parada} que desea guardar
	 * @return {@link Parada} agregado
	 */
	public Parada addParada(Parada parada) {
		if(paradaRepository.existsByNombreAndEstadoIsTrue(parada.getNombre()) && parada.getId() == null)
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Ya existe Parada con el Nombre:  "+parada.getNombre()+".");
		else return paradaRepository.save(parada);
	}
	
	/**
	 * Actualiza datos de una {@link Parada}
	 * @param parada - {@link Parada} que desea actualizar sus datos
	 * @return {@link Parada} actualizada
	 */
	public Parada updateParada(Parada parada) {
		if(!paradaRepository.existsById(parada.getId()))
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Parada no esta Registrada.");
		return paradaRepository.save(parada);
	}
	
	/**
	 * Elimina una Parada
	 * @param placa - Id de la parada a Eliminar
	 */
	public void deleteParada(String id) {
		Parada c = paradaRepository.findById(id).get();
		c.setEstado(false);
		paradaRepository.save(c);
	}
	public Parada getParadaById(String id) {
		return paradaRepository.findById(id).get();
	}
	public List<Parada> getParadasCercanasRadio(Point punto,Double longitud,String linea){
		Circle circle = new Circle(punto,new Distance((longitud*GlobalVariables.coeficiente), Metrics.KILOMETERS));
		List<String> idsParadas = rutaRepository.findById(linea).orElseThrow(() -> new ResponseStatusException(
			       HttpStatus.NOT_FOUND, "No exsiste paradas para la Line "+linea+".")).getListasParadas();
		Optional<List<Parada>> par = paradaRepository.findByCoordenadaWithin(circle);
		par.get().stream()
		            .filter(e -> idsParadas.contains(e.getId()))
		            .collect(Collectors.toList());
		return par.filter(a -> !a.isEmpty()).orElseThrow(() -> new ResponseStatusException(
			       HttpStatus.NOT_FOUND, "No exsiste paradas cercanas a su posicion de la linea "+linea+"."));
	}
	
	//TODO debe consultar por linea y realizar el query geografico
	public Iterable<Parada> getAllParadaCercanasRadio(Point punto,Double longitud){
		Circle circle = new Circle(punto,new Distance(longitud*GlobalVariables.coeficiente, Metrics.KILOMETERS));
		return paradaRepository.findByCoordenadaWithin(circle).orElseThrow(() -> new ResponseStatusException(
			       HttpStatus.NOT_FOUND, "No exsiste paradas cercanas."));
	}
	
	/**
	 * Elimina de manera permanente de la base de Datos una {@link Parada}
	 * @param id - ID de la {@link Parada} a eliminar
	 */
	public void deleteParadaPhysical(String id) {
		if(paradaRepository.existsById(id))
			paradaRepository.deleteById(id);
		else
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "No existe Parada con ID: "+id+".");
	}

	/**
	 * Elimina de manera Permanente todos las {@link Parada} registrados en la base de datos.
	 */
	public void deleteAllParadaPhysical() {
		paradaRepository.deleteAll();
	}
}