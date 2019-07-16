package com.webServices.rutas.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.model.Parada;
import com.webServices.rutas.model.Ruta;
import com.webServices.rutas.repository.ParadaRepository;
import com.webServices.rutas.repository.RutaRepository;
import com.webServices.rutas.util.Gpx;

/**
 * Contiene los Servicios de {@link Ruta} y realiza sus respectivas operaciones.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 */
@Service
public class RutaService {
	/**
	 * Instancia para el Repositorio de {@link Parada}
	 * @see {@link ParadaRepository}
	 */
	@Autowired
	private ParadaRepository paradaRepository;
	
	/**
	 * Instancia para el Repositorio de {@link Ruta}
	 * @see {@link RutaRepository}
	 */
	@Autowired
	private RutaRepository rutaRepository;
	
	/**
	 * Obtener lista de {@link Ruta}
	 * @return Lista de {@link Ruta}
	 */
	public List<Ruta> getAllRuta(){
		return rutaRepository.findByEstadoIsTrue()
				.filter(a -> !a.isEmpty())
				.orElseThrow(() ->new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Ruta Registrados."));
	}
	
	/**
	 * Obtener datos de una {@link Ruta} entregando su respectivo ID.
	 * @param id - ID de la {@link Ruta} que desee obtener los datos
	 * @return {@link Ruta}
	 */
	public Ruta getRuta(String linea) {
		return rutaRepository.findByLineaAndEstadoIsTrue(linea)
				.orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe el Reporte."));
	}
	
	/**
	 * Obtener datos de una {@link Ruta} entregando su respectiva Linea.
	 * Ignorando su estado eliminado
	 * @param linea - Linea de la {@link Ruta} que desee obtener los datos
	 * @return {@link Ruta}
	 */
	public Ruta getRutaIgnoreEstado(String linea) {
		return rutaRepository.findById(linea)
				.orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe Ruta registrado para la Linea "+linea+"."));
	}
	
	/**
	 * Obtener Lista de {@link Ruta} Ignorando su estado Eliminado
	 * @return Lista de {@link Ruta}
	 */
	public List<Ruta> getAllRutaIgnoreEstado() {
		return Optional.of((List<Ruta>) rutaRepository.findAll())
				.filter(a -> !a.isEmpty())
				.orElseThrow(() -> new ResponseStatusException(
				           HttpStatus.NOT_FOUND, "No existen Rutas Registradas."));
	}
	
	/**
	 * Agregar {@link Ruta}
	 * @param ruta - {@link Ruta} que desea guardar
	 * @return {@link Ruta} agregado
	 */
	public Ruta addRuta(Ruta ruta) {
		if(rutaRepository.existsByLineaAndEstadoIsTrue(ruta.getLinea()) || ruta.getId()!=null)
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Ya existe Cooperativa con el Nombre:  "+ruta.getLinea()+".");
		else return rutaRepository.save(ruta);
	}
	
	/**
	 * Agrega una Ruta usando un Archivo GPX e indicando la Linea de Cooperativa Correspondiente
	 * @param file - Archivo GPX
	 * @param linea - Linea de {@link Cooperativa}
	 * @return {@link Ruta} Registrada
	 * @throws IOException - Archivo no compatible.
	 */
	@SuppressWarnings("unchecked")
	public Ruta addRutaWithGPX(MultipartFile file, String linea) throws IOException {
		if(rutaRepository.existsByLineaAndEstadoIsTrue(linea))
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Ya existe Ruta para la linea:  "+linea+".");
        Map<String, Object> x = Gpx.decodeGPX(file.getBytes());
		List<Point> ruta = (List<Point>) x.get("ruta");
		List<Parada> paradas = (List<Parada>) x.get("parada");
		List<String> paradaId = new ArrayList<String>();
		for(Parada p: paradaRepository.saveAll(paradas))
			paradaId.add(p.getId());
		Ruta rutaAdd = new Ruta(linea,ruta, paradaId,null);
		return rutaRepository.save(rutaAdd);
	}
	
	/**
	 * Actualiza una Ruta usando un Archivo GPX e indicando la Linea de Cooperativa Correspondiente
	 * @param file - Archivo GPX
	 * @param linea - Linea de {@link Cooperativa}
	 * @return {@link Ruta} Actualizada
	 * @throws IOException - Archivo no compatible.
	 */
	@SuppressWarnings("unchecked")
	public Ruta updateRutaWithGPX(MultipartFile file, String linea) throws IOException {
		if(!rutaRepository.existsByLineaAndEstadoIsTrue(linea))
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "No existe Ruta para la linea: "+linea+".");
		Ruta r = rutaRepository.findById(linea).get();
		paradaRepository.deleteAll(paradaRepository.findAllById(r.getListasParadas()));
        Map<String, Object> x = Gpx.decodeGPX(file.getBytes());
		List<Point> ruta = (List<Point>) x.get("ruta");
		List<Parada> paradas = (List<Parada>) x.get("parada");
		List<String> paradaId = new ArrayList<String>();
		for(Parada p: paradaRepository.saveAll(paradas))
			paradaId.add(p.getId());
		Ruta rutaAdd = new Ruta(linea,ruta, paradaId,null);
		return rutaRepository.save(rutaAdd);
	}
	
	/**
	 * Actualiza datos de una {@link Ruta}
	 * @param ruta - {@link Ruta} que desea actualizar sus datos
	 * @return {@link Ruta} actualizada
	 */
	public Ruta updateRuta(Ruta ruta) {
		if(!rutaRepository.existsById(ruta.getId()))
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "Ruta no esta Registrada.");
		return rutaRepository.save(ruta);
	}
	
	/**
	 * Elimina una {@link Ruta}
	 * @param id - Id de la {@link Ruta} a Eliminar
	 */
	public void deleteRuta(String linea) {
		Ruta c = getRuta(linea);
		c.setEstado(false);
		rutaRepository.save(c);
	}
	
	/**
	 * Elimina de manera permanente de la base de Datos una {@link Ruta}
	 * @param linea - Linea del {@link Ruta} a eliminar
	 */
	public void deleteRutaPhysical(String linea) {
		if(rutaRepository.existsById(linea)) {
			Ruta r = rutaRepository.findById(linea).get();
			paradaRepository.deleteAll(paradaRepository.findAllById(r.getListasParadas()));
			rutaRepository.deleteById(linea);
		}else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "No existe Ruta para la Linea "+linea+".");
		}
	}
	
	/**
	 * Elimina de manera Permanente todas las {@link Ruta} registrados en la base de datos.
	 */
	public void deleteAllRutaPhysical() {
		paradaRepository.deleteAll();
		rutaRepository.deleteAll();
	}
}