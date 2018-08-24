package com.webServices.rutas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.Buses;
import com.webServices.rutas.repository.BusRepository;

@Service
public class BusService {
	@Autowired
	private BusRepository busRepository;
	public Buses getBus(String id) {
		return null;//busRepository.findOne(id);
	}
	public List<Buses> getAllBus(){
		return (List<Buses>) busRepository.findAll();
	}
	public void addBus(Buses bus) {
		busRepository.save(bus);
	}
	public void updateBus(String id,Buses bus) {
	}
	public void deleteBus(String id) {
		//busRepository.delete(id);;
	}
	public void deleteAllBus() {
		busRepository.deleteAll();
	}
}
