package com.webServices.rutas.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.webServices.rutas.model.Asunto;
import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.repository.AsuntoRepository;

/**
 * Contiene los Servicios de {@link Asunto} y realiza sus respectivas operaciones.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 */
@Service
public class AsuntoService {
	/**
	 * Instancia para el Repositorio de {@link AsuntoRepository}
	 * @see {@link AsuntoRepository}
	 */
	@Autowired
	private AsuntoRepository asuntoRepository;
	
	/**
	 * Obtiene la lista de {@link Asunto} para ser usados en el Registro de un {@link Reporte}
	 * @return Lista de {@link Asunto}
	 */
	public List<String> getAllAsunto() {
		return asuntoRepository.findById("Asuntos").orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe Asuntos Registrados.")).getAsuntos();
	}
	
	/**
	 * Añade un asunto nuevo para ser usado en {@link Reporte}.
	 * @param asunto - Asunto a añadir
	 */
	public void addAsunto(String asunto) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().add(asunto);
		asuntoRepository.save(aux);
	}
	
	/**
	 * Actualiza un Asunto registrado.
	 * @param before - Asunto a cambiar
	 * @param after - Asunto nuevo
	 */
	public void updateAsunto(String before,String after) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		Collections.replaceAll(aux.getAsuntos(),before,after);
		asuntoRepository.save(aux);
	}
	
	/**
	 * Elimina un Asunto
	 * @param asunto - Asunto a eliminar
	 */
	public void deleteAsunto(String asunto) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().remove(asunto);
		asuntoRepository.save(aux);
	}
	
	/**
	 * Crea Asuntos, es utilizado solo la primera vez del registro
	 * @param asunto - {@link Asunto} a registrar
	 */
	public void createAsunto(Asunto asunto) {
		asuntoRepository.save(asunto);
	}
}