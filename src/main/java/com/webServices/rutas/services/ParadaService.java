package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.repository.ParadaRepository;

@Service
public class ParadaService {
	@Autowired
	private ParadaRepository paradaRepository;
	
	public void addParada(Parada parada) {
		paradaRepository.save(parada);
	}
	public Iterable<Parada> getAllParada(){
		return paradaRepository.findAll();
	}
}
