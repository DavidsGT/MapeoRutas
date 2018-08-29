package com.webServices.rutas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Buses;
import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.HistorialEstadoBus;
import com.webServices.rutas.repository.BusRepository;
import com.webServices.rutas.repository.HistorialEstadoBusRepository;

@Service
public class BusService {
	@Autowired
	private BusRepository busRepository;
	@Autowired
	private HistorialEstadoBusRepository historialEstadoBusRepository;
	public Iterable<HistorialEstadoBus> getHistorialEstadoBusAllByPlaca(String placa){
		return historialEstadoBusRepository.findByIdBus(placa);
	}
	public Buses getBus(String id) {
		return busRepository.findById(id).get();
	}
	public List<Buses> getAllBus(){
		return (List<Buses>) busRepository.findAll();
	}
	public List<Buses> getAllBusEstadoTrue(){
		return (List<Buses>) busRepository.findByEstadoIsTrue();
	}
	public Buses addBus(Buses bus) {
		return busRepository.save(bus);
	}
	public void updateBus(Buses bus) {
		busRepository.save(bus);
	}
	public void deleteBus(String id) {
		Buses c = busRepository.findById(id).get();
		c.setEstado(false);
		busRepository.save(c);
	}
	public Iterable<Buses> getBusesByIdCooperativa(String idCooperativa){
		return busRepository.findByIdCooperativaAndEstadoIsTrue(idCooperativa);
	}
	public void updateEstadoBus(EstadoBus estadoBus,String id) {
		Buses c = busRepository.findById(id).get();
		c.setEstadoBus(estadoBus);
		busRepository.save(c);
		HistorialEstadoBus h = new HistorialEstadoBus(id, estadoBus);
		historialEstadoBusRepository.save(h);
	}
}