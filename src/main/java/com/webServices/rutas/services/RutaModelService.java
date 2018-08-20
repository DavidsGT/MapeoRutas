package com.webServices.rutas.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.RutaModel;
import com.webServices.rutas.repository.RutaModelRepository;
@Service
public class RutaModelService {
	@Autowired
	private RutaModelRepository rutaModelRepository;
	public RutaModel getRutaModel(String id) {
		return rutaModelRepository.findOne(id);
	}
	public RutaModel getRutaModelLinea(String linea) {
		return rutaModelRepository.findByNumRuta(linea);
	}
	public Iterable<RutaModel> getAllRutaModels(){
		return rutaModelRepository.findAll();
	}
	public void addRutaModel(RutaModel rutaModel) {
		rutaModelRepository.save(rutaModel);
	}
	public void updateRutaModel(String id,RutaModel rutaModel) {
	}
	public void deleteRutaModel(String id) {
		rutaModelRepository.deleteById(id);
	}
	public void deleteAllRutaModel() {
		rutaModelRepository.deleteAll();
	}
}
