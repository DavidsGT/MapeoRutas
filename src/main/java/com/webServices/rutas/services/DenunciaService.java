package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Denuncia;
import com.webServices.rutas.repository.DenunciaRepository;

@Service
public class DenunciaService {
	@Autowired
	private DenunciaRepository denunciaRepository;
	
	public Iterable<Denuncia> getAllDenuncia(){
		return denunciaRepository.findByEstadoIsTrue();
	}
	public Iterable<Denuncia> getAllDenunciaIgnoreEstado(){
		return denunciaRepository.findAll();
	}
	public Denuncia addDenuncia(Denuncia denuncia) {
		return denunciaRepository.save(denuncia);
	}
	public Denuncia updateDenuncia(Denuncia denuncia) {
		return denunciaRepository.save(denuncia);
	}
	public void deleteDenuncia(String id) {
		Denuncia c = denunciaRepository.findById(id).get();
		c.setEstado(false);
		denunciaRepository.save(c);
	}
}
