package com.webServices.rutas.services;

import java.util.List;
import java.util.Map;

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
	public void updateAsunto(int i,String asunto) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().set(i, asunto);
		asuntoRepository.save(aux);
	}
	public void deleteAsunto(int id) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().remove(id);
		asuntoRepository.save(aux);
	}
	public void createAsunto(Asunto asunto) {
		asuntoRepository.save(asunto);
	}

}
