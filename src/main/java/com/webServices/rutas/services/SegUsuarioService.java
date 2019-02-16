package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegMenu;
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
		return segUsuarioRepository.findByEstadoIsTrue();
	}
	public List<SegUsuario> getAllSegUsuarioIgnoreEstado(){
		return (List<SegUsuario>)segUsuarioRepository.findAll();
	}
	public SegUsuario addSegUsuario(SegUsuario segUsuario) {
		return segUsuarioRepository.save(segUsuario);
	}
	public SegUsuario updateSegUsuario(SegUsuario segUsuario) {
		return segUsuarioRepository.save(segUsuario);
	}
	public void deleteSegUsuario(String id) {
		segUsuarioRepository.deleteById(id);
	}
	public void deleteAllSegUsuario() {
		segUsuarioRepository.deleteAll();
	}
	public SegUsuario getIdUsuario(String usuario, String clave) {
		return segUsuarioRepository.findByNombreAndClave(usuario,clave);
	}
}