package com.webServices.rutas.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.repository.SegUsuarioRepository;

@Service
public class SegUsuarioService {
	@Autowired
	private SegUsuarioRepository segUsuarioRepository;
	public SegUsuario getSegUsuario(String id) {
		return segUsuarioRepository.findById(id).get();
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
		segUsuarioRepository.deleteById(id);
	}
	public void deleteAllSegUsuario() {
		segUsuarioRepository.deleteAll();
	}
}