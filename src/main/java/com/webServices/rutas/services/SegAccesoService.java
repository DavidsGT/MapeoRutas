package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegAcceso;
import com.webServices.rutas.repository.SegAccesoRepository;

@Service
public class SegAccesoService {
	@Autowired
	private SegAccesoRepository segAccesoRepository;
	public SegAcceso getSegAcceso(String id) {
		return segAccesoRepository.findById(id).get();
	}
	public List<SegAcceso> getAllSegAccesoIgnoreEstado(){
		return (List<SegAcceso>)segAccesoRepository.findAll();
	}
	public Iterable<SegAcceso> getAllSegAcceso(){
		return segAccesoRepository.findByEstadoIsTrue();
	}
	public SegAcceso addSegAcceso(SegAcceso segAcceso) {
		return segAccesoRepository.save(segAcceso);
	}
	public SegAcceso updateSegAcceso(SegAcceso segAcceso) {
		return segAccesoRepository.save(segAcceso);
	}
	public void deleteSegAcceso(String id) {
		segAccesoRepository.deleteById(id);
	}
	public void deleteAllSegAcceso() {
		segAccesoRepository.deleteAll();
	}
}