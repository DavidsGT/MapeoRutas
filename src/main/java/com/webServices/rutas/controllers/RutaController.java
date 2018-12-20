package com.webServices.rutas.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webServices.rutas.model.Ruta;
import com.webServices.rutas.services.RutaService;

/**
 * Clase que contiene los requestMapping de Ruta y los asocia a sus respectivos servicios en {@link RutaService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see RutaService
 * @version 1.0
 */
@RestController
@RequestMapping("rutas")
public class RutaController {
	
	/**
	 * Instancia de los Servicios para Ruta
	 */
	@Autowired
	private RutaService rutaService;
	
	/**
	 * Metodo que Mapea "/rutas", RequestMethod es GET, se enlaza al servicio {@link RutaService#getAllRuta())}
	 * y retorna Rutas registrados
	 * @return Lista de Rutas
	 * @see {@link RutaService#getAllRuta()}
	 */
	@GetMapping
	public List<Ruta> getAllRuta(){
		return rutaService.getAllRuta();
	}
	
	/**
	 * Metodo que Mapea "/rutas/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link RutaService#getAllRutaIgnoreEstado()} 
	 * y retorna todos las Rutas incluye eliminados logicamente.
	 * @return Lista de Rutas incluye eliminados logicamente.
	 * @see {@link RutaService#getAllRutaIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	public List<Ruta> getAllRutaIgnoreEstado(){
		return rutaService.getAllRutaIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/rutas/{linea}", RequestMethod es GET, se enlaza al servicio {@link RutaService#getRuta(String)} 
	 * y retorna la Ruta
	 * @param linea - Linea de la Ruta
	 * @return Ruta
	 * @see {@link RutaService#getRuta(String)} 
	 */
	@GetMapping("/{linea}")
	public Ruta getRuta(@PathVariable String linea) {
		return rutaService.getRuta(linea);
	}
	
	/**
	 * Metodo que Mapea "/rutas/archivoGPX", RequestMethod es POST, se enlaza al servicio {@link RutaService#addRutaWithGPX(MultipartFile, String)} 
	 * y retorna la Ruta Guardada mediante un archivo GPX.
	 * @param file - Archivo GPX de donde escogera la Ruta y sus Paradas
	 * @param linea - Linea a la que pertenece esta ruta.
	 * @return
	 * @see {@link RutaService#addRutaWithGPX(MultipartFile, String)}
	 * @throws IOException - en caso de que el archivo no sea GPX
	 */
	@PostMapping("/archivoGPX")
	public Ruta addRutaWithGPX(@RequestParam("file") MultipartFile file,@RequestParam("linea") String linea) throws IOException {
		System.out.println("Hola");
		return rutaService.addRutaWithGPX(file, linea);
	}
	
	/**
	 * Metodo que Mapea "/rutas", RequestMethod es POST, se enlaza al servicio {@link RutaService#addRutaModel(Ruta)} 
	 * y retorna Ruta registrada
	 * @param ruta - Ruta a Registrar
	 * @return Ruta Registrada
	 * @see {@link RutaService#addRutaModel(Ruta)}
	 */
	@PostMapping
	public Ruta addRuta(@RequestBody Ruta ruta) {
		return rutaService.addRuta(ruta);
	}
	
	/**
	 * Metodo que Mapea "/rutas", RequestMethod es PUT, se enlaza al servicio {@link RutaService#updateRuta(Ruta)}.
	 * Actualizar Ruta.
	 * @param ruta - Ruta a Actualizar
	 * @see {@link RutaService#updateRuta(Ruta)}
	 */
	@PutMapping
	public Ruta updateRutaModel(@RequestBody Ruta ruta) {
		return rutaService.updateRuta(ruta);
	}
	
	/**
	 * Metodo que Mapea "/ruta/{id}", RequestMethod es DELETE, se enlaza al servicio {@link RutaService#deleteRuta(String)}.
	 * Eliminar una Ruta.
	 * @param id - Id de la Ruta
	 * @see {@link RutaService#deleteRuta(String)}
	 */
	@DeleteMapping("/{id}")
	public void deleteRuta(@PathVariable String id) {
		rutaService.deleteRuta(id);
	}
}