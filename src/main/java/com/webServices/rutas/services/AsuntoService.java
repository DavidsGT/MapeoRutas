package com.webServices.rutas.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Asunto;
import com.webServices.rutas.repository.AsuntoRepository;

@Service
public class AsuntoService {
	@Autowired
	private AsuntoRepository asuntoRepository;
	public Map<String,String> getAllAsunto() {
		return asuntoRepository.findById("Asuntos").get().getAsuntos();
	}
	public void addAsunto(String asunto) {
		System.out.println(asuntoRepository.findById("Asuntos").get());
		/*Integer count = asuntoRepository.findById("Asuntos").get().getAsuntos().size();
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().put(count+1, asunto);
		asuntoRepository.save(aux);*/
	}
	public void updateAsunto(String id,String asunto) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().put(id, asunto);
		asuntoRepository.save(aux);
	}
	public void deleteAsunto(Integer id) {
		Asunto aux = asuntoRepository.findById("Asuntos").get();
		aux.getAsuntos().remove(id);
		asuntoRepository.save(aux);
	}
	public void createAsunto(Asunto asunto) {
		asuntoRepository.save(asunto);
	}

}
