package com.webServices.rutas.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.model.SegPerfil;
import com.webServices.rutas.repository.SegPerfilRepository;

/**
 * Contiene los Servicios de {@link SegPerfil} y realiza sus respectivas operaciones.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 */
@Service
public class SegPerfilService{
	/**
	 * Instancia para el Repositorio de {@link SegPerfilRepository}
	 * @see {@link SegPerfilRepository}
	 */
	@Autowired
	private SegPerfilRepository segPerfilRepository;
	
	/**
	 * Obtiene la lista de {@link SegPerfil} para ser usados en el Registro de un {@link Reporte}
	 * @return Lista de {@link SegPerfil}
	 */
	public List<String> getAllSegPerfil(){
		return segPerfilRepository.findById("Perfiles").orElseThrow(() -> new ResponseStatusException(
		           HttpStatus.NOT_FOUND, "No existe Perfiles Registrados.")).getMyMap();
	}
	
	/**
	 * Añade un Perfil nuevo.
	 * @param perfil - Perfil a añadir
	 */
	public void addSegPerfil(String segPerfil) {
		SegPerfil aux = segPerfilRepository.findById("Perfiles").get();
		aux.getMyMap().add(segPerfil);
		segPerfilRepository.save(aux);
	}
	
	/**
	 * Actualiza un Perfil registrado.
	 * @param before - Perfil a cambiar
	 * @param after - Perfil nuevo
	 */
	public void updateSegPerfil(String before,String after) {
		SegPerfil aux = segPerfilRepository.findById("Perfiles").get();
		Collections.replaceAll(aux.getMyMap(),before,after);
		segPerfilRepository.save(aux);
	}
	
	/**
	 * Elimina un Perfil
	 * @param perfil - Perfil a eliminar
	 */
	public void deleteSegPerfil(String perfil) {
		SegPerfil aux = segPerfilRepository.findById("Perfiles").get();
		aux.getMyMap().remove(perfil);
		segPerfilRepository.save(aux);
	}
	
	/**
	 * Crea Perfiles, es utilizado solo la primera vez del registro
	 * @param perfil - {@link SegPerfil} a registrar
	 */
	public void createPerfil(SegPerfil perfil) {
		segPerfilRepository.save(perfil);
	}
}
