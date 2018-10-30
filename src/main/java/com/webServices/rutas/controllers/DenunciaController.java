package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Denuncia;
import com.webServices.rutas.services.DenunciaService;

/**
 * Contiene los requestMapping de Denuncias y los asocia a sus respectivos servicios en {@link DenunciaService}.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 * @see DenunciaService
 */
@RestController("/denuncias")
public class DenunciaController {
	
	/**
	 * Instancia de los servicios para Denuncias
	 */
	@Autowired
	private DenunciaService denunciaService;
	
	/**
	 * Metodo que Mapea "/denuncias", RequestMethod es GET, se enlaza al servicio {@link DenunciaService#getAllDenuncia()} y retorna datos de todos las denuncias registradas
	 * @return Lista de Denuncias
	 * @see {@link DenunciaService#getAllDenuncia()}
	 */
	@GetMapping
	public Iterable<Denuncia> getAllDenuncia(){
		return denunciaService.getAllDenuncia();
	}
	
	/**
	 * Metodo que Mapea "/denuncias/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link DenunciaService#getAllDenunciasEstadoTrue()} 
	 * y retorna todos los denuncias incluye eliminados logicamente.
	 * @return Denuncias incluye eliminados logicamente
	 * @see {@link DenunciaService#getAllDenunciasEstadoTrue()}
	 */
	@GetMapping("/ignoreEstado")
	public Iterable<Denuncia> getAllDenunciaIgnoreEstado(){
		return denunciaService.getAllDenunciaIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/denuncias", RequestMethod es POST, se enlaza al servicio {@link DenunciaService#addDenuncia(Denuncia)} 
	 * y retorna Datos de una denuncia registrada
	 * @param denuncia - Datos de la Denuncia a Registrar
	 * @return denuncia Registrado
	 * @see {@link DenunciaService#addDenuncia(Denuncia)}
	 */
	@PostMapping
	public Denuncia addDenuncia(@RequestBody Denuncia denuncia) {
		return denunciaService.addDenuncia(denuncia);
	}
	
	/**
	 * Metodo que Mapea "/denuncias", RequestMethod es PUT, se enlaza al servicio {@link DenunciaService#updateDenuncia(Denuncia)}.
	 * Actualizar Denuncia.
	 * @param denuncia - Denuncia a Actualizar
	 * @return Denuncia Actualizada
	 * @see {@link DenunciaService#updateDenuncia(Denuncia)}
	 */
	@PutMapping
	public Denuncia updateDenuncia(@RequestBody Denuncia denuncia) {
		return denunciaService.updateDenuncia(denuncia);
	}
	
	/**
	 * Metodo que Mapea "/denuncia/{id}", RequestMethod es DELETE, se enlaza al servicio {@link DenunciaService#deleteDenuncia(String)}.
	 * Eliminar un Denuncia.
	 * @param id - Id de la Denuncia a Eliminar
	 * @see {@link DenunciaService#deleteDenuncia(String)}
	 */
	@DeleteMapping
	public void deleteDenuncia(@PathVariable String id) {
		denunciaService.deleteDenuncia(id);
	}
}