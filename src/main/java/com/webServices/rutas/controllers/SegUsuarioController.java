package com.webServices.rutas.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegUsuario;
import com.webServices.rutas.services.SegUsuarioService;

@RestController
public class SegUsuarioController {
	@Autowired
	private SegUsuarioService segUsuarioService;
	@RequestMapping("/usuarios")
	public Iterable<SegUsuario> getAllUsuarios(){
		return segUsuarioService.getAllSegUsuario();
	}
	@RequestMapping("/usuarios/{id}")
	public SegUsuario getUsuario(@PathVariable String id) {
		return segUsuarioService.getSegUsuario(id);
	}
	@RequestMapping(method=RequestMethod.POST, value="/usuarios")
	public void addSegUsuario(@RequestBody SegUsuario segUsuario) {
		segUsuarioService.addSegUsuario(segUsuario);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/usuarios/{id}")
	public void updateSegUsuario(@RequestBody SegUsuario segUsuario,@PathVariable String id) {
		segUsuarioService.updateSegUsuario(id, segUsuario);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/usuarios/{id}")
	public void deleteSegUsuario(@PathVariable String id) {
		segUsuarioService.deleteSegUsuario(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/usuarios")
	public void deleteSegUsuario() {
		segUsuarioService.deleteAllSegUsuario();
	}
}
