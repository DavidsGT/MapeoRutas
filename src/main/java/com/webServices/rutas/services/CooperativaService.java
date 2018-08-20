package com.webServices.rutas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Cooperativa;
import com.webServices.rutas.repository.CooperativaRepository;

@Service
public class CooperativaService {
	@Autowired
	private CooperativaRepository cooperativaRepository;
	public Cooperativa getCooperativa(String id) {
		return cooperativaRepository.findOne(id);
	}
	public List<Cooperativa> getAllCooperativa(){
		return (List<Cooperativa>) cooperativaRepository.findAll();
	}
	public void addCooperativa(Cooperativa cooperativa) {
		cooperativaRepository.save(cooperativa);
	}
	public void updateCooperativa(String id, Cooperativa cooperativa) {
	}
	public void deleteCooperativa(String id) {
		cooperativaRepository.delete(id);
	}
	public void deleteAllCooperativa(){
		cooperativaRepository.deleteAll();
	}
}
