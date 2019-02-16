package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegPerfil;
import com.webServices.rutas.repository.SegPerfilRepository;

@Service
public class SegPerfilService{
	@Autowired
	private SegPerfilRepository segPerfilRepository;
	public SegPerfil getSegPerfil(String id) {
		return segPerfilRepository.findById(id).get();
	}
	public Iterable<SegPerfil> getAllSegPerfil(){
		return segPerfilRepository.findByEstadoIsTrue();
	}
	public Iterable<SegPerfil> getAllSegPerfilIgnoreEstado(){
		return segPerfilRepository.findAll();
	}
	public SegPerfil addSegPerfil(SegPerfil segPerfil) {
		return segPerfilRepository.save(segPerfil);
	}
	public SegPerfil updateSegPerfil(SegPerfil segPerfil) {
		return segPerfilRepository.save(segPerfil);
	}
	public void deleteSegPerfil(String id) {
		segPerfilRepository.deleteById(id);
	}
	public void deleteAllSegPerfil() {
		segPerfilRepository.deleteAll();
	}
}
