package com.webServices.rutas.controllers;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webServices.rutas.model.Ruta;
import com.webServices.rutas.services.RutaService;

@RestController
public class RutaController {
	@Autowired
	private RutaService rutaService;
	@RequestMapping("/rutas")
	public Iterable<Ruta> getAllPuntos(){
		return rutaService.getAllRutaModels();
	}
	@RequestMapping("/rutas/{id}")
	public Ruta getPunto(@PathVariable String id) {
		return rutaService.getRutaModel(id);
	}
	@RequestMapping(method=RequestMethod.GET,value="/rutas/linea/{linea}")
	public Ruta getPuntoLinea(@PathVariable String linea) {
		return rutaService.getRutaModelLinea(linea);
	}
	
	@PostMapping("/archivoGPX")
	public Ruta addRutaWithGPX(@RequestParam("file") MultipartFile file,@RequestParam("linea") String linea) throws IllegalStateException, IOException, ParserConfigurationException {
		Ruta ruta = rutaService.addRutaWithGPX(file, linea);
        return ruta;
    }
	
	@RequestMapping(method=RequestMethod.POST, value="/rutas")
	public void addRutaModel(@RequestBody Ruta rutaModel) {
		rutaService.addRutaModel(rutaModel);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/rutas/{id}")
	public void updateRutaModel(@RequestBody Ruta rutaModel,@PathVariable String id) {
		rutaService.updateRutaModel(id, rutaModel);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/rutas/{id}")
	public void deleteRutaModel(@PathVariable String id) {
		rutaService.deleteRutaModel(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/rutas")
	public void deleteRutaModels() {
		rutaService.deleteAllRutaModel();
	}
}
