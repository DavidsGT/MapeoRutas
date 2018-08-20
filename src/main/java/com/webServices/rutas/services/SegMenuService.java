package com.webServices.rutas.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.SegMenu;
import com.webServices.rutas.repository.SegMenuRepository;

@Service
public class SegMenuService {
	@Autowired
	private SegMenuRepository segMenuRepository;
	public SegMenu getSegMenu(String id) {
		return segMenuRepository.findOne(id);
	}
	public Iterable<SegMenu> getAllSegMenu(){
		return segMenuRepository.findAll();
	}
	public void addSegMenu(SegMenu segMenu) {
		segMenuRepository.save(segMenu);
	}
	public void updateSegMenu(String id,SegMenu segMenu) {
	}
	public void deleteSegMenu(String id) {
		segMenuRepository.deleteById(id);
	}
	public void deleteAllSegMenu() {
		segMenuRepository.deleteAll();
	}
}
