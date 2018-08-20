package com.webServices.rutas.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegMenu;
import com.webServices.rutas.services.SegMenuService;

@RestController
public class SegMenuController {
	@Autowired
	private SegMenuService segMenuService;
	@RequestMapping("/menus")
	public Iterable<SegMenu> getAllSegMenu(){
		return segMenuService.getAllSegMenu();
	}
	@RequestMapping("/menus/{id}")
	public SegMenu getSegMenu(@PathVariable String id) {
		return segMenuService.getSegMenu(id);
	}
	@RequestMapping(method=RequestMethod.POST, value="/menus")
	public void addSegMenu(@RequestBody SegMenu segMenu) {
		segMenuService.addSegMenu(segMenu);
	}
	@RequestMapping(method=RequestMethod.PUT, value="/menus/{id}")
	public void updateSegMenu(@RequestBody SegMenu segMenu,@PathVariable String id) {
		segMenuService.updateSegMenu(id, segMenu);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/menus/{id}")
	public void deleteSegMenu(@PathVariable String id) {
		segMenuService.deleteSegMenu(id);
	}
	@RequestMapping(method=RequestMethod.DELETE, value="/menus")
	public void deleteSegMenu() {
		segMenuService.deleteAllSegMenu();
	}
}
