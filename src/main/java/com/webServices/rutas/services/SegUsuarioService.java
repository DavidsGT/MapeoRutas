package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
		System.out.println(segUsuarioRepository.count());
		return segUsuarioRepository.findByEstadoIsTrue();
	}
	public List<SegUsuario> getAllSegUsuarioIgnoreEstado(){
		return (List<SegUsuario>)segUsuarioRepository.findAll();
	}
	public SegUsuario addSegUsuario(SegUsuario segUsuario) {
		SegUsuario u = segUsuarioRepository.findByUsuarioOrEmail(segUsuario.getUsuario(),segUsuario.getEmail());
		if(u == null) {
			return segUsuarioRepository.save(segUsuario);
		}else {
			if(segUsuario.getEmail().equals(u.getEmail())){
				throw new ResponseStatusException(
				           HttpStatus.CONFLICT, "Email Existente");
			}
			else {
				throw new ResponseStatusException(
				           HttpStatus.CONFLICT, "Nombre de Usuario Existente");
			}
			
		}
	}
	public SegUsuario updateSegUsuario(SegUsuario segUsuario) {
		return segUsuarioRepository.save(segUsuario);
	}
	public void deleteSegUsuario(String id) {
		SegUsuario u = segUsuarioRepository.findById(id).get();
		u.setEstado(false);
		segUsuarioRepository.save(u);
	}
	public void deleteAllSegUsuario() {
		segUsuarioRepository.deleteAll();
	}
	public SegUsuario getIdUsuario(String usuario, String clave) {
		SegUsuario u = segUsuarioRepository.findByUsuarioOrEmail(usuario,usuario);
		if(u == null) {
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}else{
			if(u.getClave().equals(clave)) {
				return u;
			}else{
				throw new ResponseStatusException(
				       HttpStatus.CONFLICT, "Contraseña no valida");
			}
		}
		
	}
}