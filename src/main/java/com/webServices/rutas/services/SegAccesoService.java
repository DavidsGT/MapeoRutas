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
public class SegAccesoService {
	@Autowired
	private SegAccesoRepository segAccesoRepository;
	@Autowired
	private SegUsuarioRepository segUsuarioRepository;
	@Autowired
	private SegMenuRepository segMenuRepository;
	public SegAcceso getSegAcceso(String id) {
		return segAccesoRepository.findById(id).get();
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