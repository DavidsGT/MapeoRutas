package com.webServices.rutas.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Asunto;
import com.webServices.rutas.repository.AsuntoRepository;

@Service
public class AsuntoService {
	@Autowired
	private AsuntoRepository asuntoRepository;
	public List<String> getAllAsunto() {
		return asuntoRepository.findById("Asuntos").get().getAsuntos();
	}
	public void addAsunto(String asunto) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().add(asunto);
		asuntoRepository.save(aux);
	}
	public void updateAsunto(String before,String after) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		Collections.replaceAll(aux.getAsuntos(),before,after);
		asuntoRepository.save(aux);
	}
	public void deleteAsunto(String perfil) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().remove(perfil);
		asuntoRepository.save(aux);
	}
	public void createAsunto(Asunto asunto) {
		asuntoRepository.save(asunto);
	}
}