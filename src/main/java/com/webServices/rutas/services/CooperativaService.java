package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.repository.CooperativaRepository;

@Service
public class CooperativaService {
	
	@Autowired
	private CooperativaRepository cooperativaRepository;
	
	public List<Cooperativa> getAllCooperativa(){
		return (List<Cooperativa>) cooperativaRepository.findByEstadoIsTrue();
	}
	public Cooperativa getCooperativa(String id) {
		return cooperativaRepository.findById(id).get();
	}
	public Cooperativa getCooperativaByNombre(String nombre) {
		System.out.println(nombre);
		return cooperativaRepository.findByNombre(nombre);
	}
	public List<Cooperativa> getAllCooperativaIgnoreEstado(){
		return (List<Cooperativa>) cooperativaRepository.findAll();
	}
	public Cooperativa addCooperativa(Cooperativa cooperativa) {
		return cooperativaRepository.save(cooperativa);
	}
	public Cooperativa updateCooperativa(Cooperativa cooperativa) {
		return cooperativaRepository.save(cooperativa);
	}
	public void deleteCooperativa(String id) {
		Cooperativa c = cooperativaRepository.findById(id).get();
		c.setEstado(false);
		cooperativaRepository.save(c);
	}
}
