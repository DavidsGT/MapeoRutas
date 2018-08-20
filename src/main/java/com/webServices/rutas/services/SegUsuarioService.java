package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.repository.SegUsuarioRepository;

@Service
public class SegUsuarioService {
	@Autowired
	private SegUsuarioRepository segUsuarioRepository;
	public SegUsuario getSegUsuario(String id) {
		return segUsuarioRepository.findOne(id);
	}
	public Iterable<SegUsuario> getAllSegUsuario(){
		return segUsuarioRepository.findAll();
	}
	public void addSegUsuario(SegUsuario segUsuario) {
		segUsuarioRepository.save(segUsuario);
	}
	public void updateSegUsuario(String id,SegUsuario segUsuario) {
	}
	public void deleteSegUsuario(String id) {
		segUsuarioRepository.delete(id);
	}
	public void deleteAllSegUsuario() {
		segUsuarioRepository.deleteAll();
	}
}
