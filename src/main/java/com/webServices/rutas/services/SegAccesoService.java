package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegAcceso;
import com.webServices.rutas.repository.SegAccesoRepository;

@Service
public class SegAccesoService {
	@Autowired
	private SegAccesoRepository segAccesoRepository;
	public SegAcceso getSegAcceso(String id) {
		return segAccesoRepository.findById(id);
	}
	public Iterable<SegAcceso> getAllSegAcceso(){
		return segAccesoRepository.findAll();
	}
	public void addSegAcceso(SegAcceso segAcceso) {
		segAccesoRepository.save(segAcceso);
	}
	public void updateSegAcceso(String id,SegAcceso segAcceso) {
	}
	public void deleteSegAcceso(String id) {
		segAccesoRepository.deleteById(id);
	}
	public void deleteAllSegAcceso() {
		segAccesoRepository.deleteAll();
	}
}