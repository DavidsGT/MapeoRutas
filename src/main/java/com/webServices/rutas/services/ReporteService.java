package com.webServices.rutas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.repository.ReporteRepository;

@Service
public class ReporteService {
	@Autowired
	private ReporteRepository reporteRepository;
	public Reporte getReporte(String id){
		return reporteRepository.findById(id).get();
	}
	public Iterable<Reporte> getAllReporte(){
		return reporteRepository.findAll();
	}
	public Reporte addDenuncia(Reporte reporte) {
		return reporteRepository.save(reporte);
	}
	public Reporte updateDenuncia(Reporte reporte) {
		return reporteRepository.save(reporte);
	}
}
