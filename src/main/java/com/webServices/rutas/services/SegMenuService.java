package com.webServices.rutas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegAcceso;
import com.webServices.rutas.model.SegMenu;
import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.repository.SegAccesoRepository;
import com.webServices.rutas.repository.SegMenuRepository;
import com.webServices.rutas.repository.SegUsuarioRepository;

@Service
public class SegMenuService {
	@Autowired
	private SegAccesoRepository segAccesoRepository;
	@Autowired
	private SegMenuRepository segMenuRepository;
	@Autowired
	private SegUsuarioRepository segUsuarioRepository;
	public SegMenu getSegMenu(String id) {
		return segMenuRepository.findById(id).get();
	}
	public Iterable<SegMenu> getAllSegMenu(){
		return segMenuRepository.findByEstadoIsTrue();
	}
	public List<SegMenu> getAllSegMenuIgnoreEstado(){
		return (List<SegMenu>)segMenuRepository.findAll();
	}
	public SegMenu addSegMenu(SegMenu segMenu) {
		return segMenuRepository.save(segMenu);
	}
	public SegMenu updateSegMenu(SegMenu segMenu) {
		return segMenuRepository.save(segMenu);
	}
	public void deleteSegMenu(String id) {
		segMenuRepository.deleteById(id);
	}
	public void deleteAllSegMenu() {
		segMenuRepository.deleteAll();
	}
	public Iterable<SegMenu> getObtenerMenus(String nombre,String clave) {
		SegUsuario Usuario = segUsuarioRepository.findByNombreAndClave(nombre,clave);
		Iterable <SegAcceso> listAccesos = segAccesoRepository.findByIdSegPerfil(Usuario.getIdSegPerfil());
		List<SegMenu> listMenu = new ArrayList<>();
		for(SegAcceso a:listAccesos) {
			listMenu.add(segMenuRepository.findById(a.getIdSegMenu()).get());
		}
		return listMenu;
	}
}
