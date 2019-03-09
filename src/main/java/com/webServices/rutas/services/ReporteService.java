package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.repository.ReporteRepository;

@Service
public class ReporteService {
	@Autowired
	private ReporteRepository reporteRepository;
	public Iterable<Reporte> getAllReporte(){
		return reporteRepository.findByEstadoIsTrue();
	}
	public Reporte getReporte(String id){
		return reporteRepository.findById(id).get();
	}
	public List<Reporte> getAllReporteIgnoreEstado() {
		return (List<Reporte>) reporteRepository.findAll();
	}
	public Reporte addReporte(Reporte reporte) {
		return reporteRepository.save(reporte);
	}
	public Reporte updateReporte(Reporte reporte) {
		return reporteRepository.save(reporte);
	}
	public void deleteReporte(String id) {
		Reporte c = reporteRepository.findById(id).get();
		c.setEstado(false);
		reporteRepository.save(c);
	}
	
}
