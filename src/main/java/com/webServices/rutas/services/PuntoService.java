package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Punto;
import com.webServices.rutas.repository.PuntoRepository;

@Service
public class PuntoService {
	@Autowired
	private PuntoRepository puntoRepository;
	public Punto getPunto(String id) {
		return puntoRepository.findById(id);
	}
	public List<Punto> getAllPuntos(){
		return puntoRepository.findAll();
	}
	public void addPunto(List<Punto> punto) {
		puntoRepository.save(punto);
	}
	public void updatePunto(String id,Punto punto) {
		
	}
	public void deletePunto(String id) {
		puntoRepository.deleteById(id);
	}
	public void deleteAllPunto() {
		puntoRepository.deleteAll();
	}
}
