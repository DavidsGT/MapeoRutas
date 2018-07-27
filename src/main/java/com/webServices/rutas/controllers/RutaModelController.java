package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.RutaModel;
import com.webServices.rutas.services.RutaModelService;

@RestController
public class RutaModelController {
	@Autowired
	private RutaModelService rutaModelService;
	@RequestMapping("/rutas")
	public Iterable<RutaModel> getAllPuntos(){
		return rutaModelService.getAllRutaModels();
	}
	@RequestMapping("/rutas/{id}")
	public RutaModel getPunto(@PathVariable String id) {
		return rutaModelService.getRutaModel(id);
	}
	@RequestMapping("/rutas/linea/{linea}")
	public RutaModel getPuntoLinea(@PathVariable String linea) {
		return rutaModelService.getRutaModelLinea(linea);
	}
	@RequestMapping(method=RequestMethod.POST, value="/rutas")
	public void addRutaModel(@RequestBody RutaModel rutaModel) {
		rutaModelService.addRutaModel(rutaModel);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/rutas/{id}")
	public void updateRutaModel(@RequestBody RutaModel rutaModel,@PathVariable String id) {
		rutaModelService.updateRutaModel(id, rutaModel);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/rutas/{id}")
	public void deleteRutaModel(@PathVariable String id) {
		rutaModelService.deleteRutaModel(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/rutas")
	public void deleteRutaModels() {
		rutaModelService.deleteAllRutaModel();
	}
}
