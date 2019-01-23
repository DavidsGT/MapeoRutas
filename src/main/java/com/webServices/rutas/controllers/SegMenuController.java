package com.webServices.rutas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.SegMenu;
import com.webServices.rutas.services.SegAccesoService;
import com.webServices.rutas.services.SegMenuService;

/**
 * Clase que contiene los requestMapping de Menu y los asocia a sus respectivos servicios en {@link SegMenuService}.
 * @author Davids Adrian Gonzalez Tigrero 
 * @see SegMenuService
 * @version 1.0
 */
@RestController
@RequestMapping("menus")
public class SegMenuController {
	
	/**
	 * Instancia de los Servicios para Menu
	 */
	@Autowired
	private SegMenuService segMenuService;
	
	/**
	 * Metodo que Mapea "/menus", RequestMethod es GET, se enlaza al servicio {@link SegMenuService#getAllSegMenu()}
	 * y retorna datos de todos las Menu registrados
	 * @return Lista de Menu
	 * @see {@link SegMenuService#getAllSegMenu()}
	 */
	@GetMapping
	public Iterable<SegMenu> getAllSegMenu(){
		return segMenuService.getAllSegMenu();
	}
	
	/**
	 * Metodo que Mapea "/menus/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link SegMenuService#getAllSegMenuIgnoreEstado()} 
	 * y retorna todos las Menu incluye eliminados logicamente.
	 * @return Lista de Menu incluye eliminados logicamente.
	 * @see {@link SegMenuService#getAllSegMenuIgnoreEstado()}
	 */
	@GetMapping("/ignoreEstado")
	public List<SegMenu> getAllReporteIgnoreEstado(){
		return segMenuService.getAllSegMenuIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/menus/{id}", RequestMethod es GET, se enlaza al servicio {@link SegMenuService#getSegMenu(String)} 
	 * y retorna el Menu
	 * @param id - Id del Menu 
	 * @return Menu
	 * @see {@link SegMenuService#getSegMenu(String)} 
	 */
	@GetMapping("/{id}")
	public SegMenu getSegMenu(@PathVariable String id) {
		return segMenuService.getSegMenu(id);
	}
	
	/**
	 * Metodo que Mapea "/menus", RequestMethod es POST, se enlaza al servicio {@link SegMenuService#addSegMenu(SegMenu)} 
	 * y retorna Datos de una Menu registrada
	 * @param SegMenu - Datos de la Menu a Registrar
	 * @return menu Registrado
	 * @see {@link SegMenuService#addSegMenu(SegMenu)}
	 */
	@PostMapping
	public SegMenu addSegMenu(@RequestBody SegMenu segMenu) {
		return segMenuService.addSegMenu(segMenu);
	}

	/**
	 * Metodo que Mapea "/menus", RequestMethod es PUT, se enlaza al servicio {@link SegMenuService#updateSegMenu(SegMenu)}.
	 * Actualizar Menu.
	 * @param segMenu - Menu a Actualizar
	 * @return Menu Actualizada
	 * @see {@link SegMenuService#updateSegMenu(SegMenu)}
	 */
	@PutMapping
	public SegMenu updateSegMenu(@RequestBody SegMenu segMenu) {
		return segMenuService.updateSegMenu(segMenu);
	}
	
	/**
	 * Metodo que Mapea "/menus/{id}", RequestMethod es DELETE, se enlaza al servicio {@link SegMenuService#deleteSegMenu(String)}.
	 * Eliminar un Menu.
	 * @param id - Id del Menu
	 * @see {@link SegMenuService#deleteSegMenu(String)}
	 */
	@DeleteMapping("/{id}")
	public void deleteSegMenu(@PathVariable String id) {
		segMenuService.deleteSegMenu(id);
	}
	/**
	 * Metodo que Mapea "/menus/us:{usuario}&cl:{clave}", RequestMethod es GET, se enlaza al servicio {@link SegAccesoService#getObtenerMenus(String, String)}.
	 * @param usuario - Nombre de Usuario
	 * @param clave - Clave del Usuario
	 * @return El menu a la que el usuario tiene acceso
	 */
	@GetMapping("/us:{usuario}&cl:{clave}/menu")
	public Iterable<SegMenu> getMenus(@PathVariable String usuario,@PathVariable String clave) {
		return segMenuService.getObtenerMenus(usuario,clave);
	}
}
