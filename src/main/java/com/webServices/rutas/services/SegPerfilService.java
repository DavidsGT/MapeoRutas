package com.webServices.rutas.services;

import java.util.Collections;
import java.util.List;

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
	public List<String> getAllSegPerfil(){
		return segPerfilRepository.findById("Perfiles").get().getMyMap();
	}
	public Iterable<SegPerfil> getAllSegPerfilIgnoreEstado(){
		return segPerfilRepository.findAll();
	}
	public void addSegPerfil(String segPerfil) {
		SegPerfil aux = segPerfilRepository.findById("Perfiles").get();
		aux.getMyMap().add(segPerfil);
		segPerfilRepository.save(aux);
	}
	public void updateSegPerfil(String before,String after) {
		SegPerfil aux = segPerfilRepository.findById("Perfiles").get();
		Collections.replaceAll(aux.getMyMap(),before,after);
		segPerfilRepository.save(aux);
	}
	public void deleteSegPerfil(String perfil) {
		SegPerfil aux = segPerfilRepository.findById("Perfiles").get();
		aux.getMyMap().remove(perfil);
		segPerfilRepository.save(aux);
	}
	public void deleteAllSegPerfil() {
		segPerfilRepository.deleteAll();
	}
	public void createPerfil(SegPerfil perfil) {
		segPerfilRepository.save(perfil);
	}
}
