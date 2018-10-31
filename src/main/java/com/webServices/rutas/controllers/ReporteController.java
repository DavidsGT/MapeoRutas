package com.webServices.rutas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webServices.rutas.model.Denuncia;
import com.webServices.rutas.model.Reporte;
import com.webServices.rutas.services.DenunciaService;
import com.webServices.rutas.services.ReporteService;

@RestController
@RequestMapping("reportes")
public class ReporteController {
	@Autowired
	private ReporteService reporteService;
	/**
	 * Metodo que Mapea "/denuncias", RequestMethod es GET, se enlaza al servicio {@link DenunciaService#getAllDenuncia()} y retorna datos de todos las denuncias registradas
	 * @return Lista de Denuncias
	 * @see {@link DenunciaService#getAllDenuncia()}
	 */
	@GetMapping
	public Iterable<Reporte> getAllReporte(){
		return reporteService.getAllReporte();
	}
	@GetMapping("/{id}")
	public Reporte getReporte(@PathVariable String id){
		return reporteService.getReporte(id);
	}
	/**
	 * Metodo que Mapea "/denuncias", RequestMethod es POST, se enlaza al servicio {@link DenunciaService#addDenuncia(Denuncia)} 
	 * y retorna Datos de una denuncia registrada
	 * @param denuncia - Datos de la Denuncia a Registrar
	 * @return denuncia Registrado
	 * @see {@link DenunciaService#addDenuncia(Denuncia)}
	 */
	@PostMapping
	public Reporte addReporte(@RequestBody Reporte reporte) {
		return reporteService.addDenuncia(reporte);
	}
	
	/**
	 * Metodo que Mapea "/denuncias", RequestMethod es PUT, se enlaza al servicio {@link DenunciaService#updateDenuncia(Denuncia)}.
	 * Actualizar Denuncia.
	 * @param denuncia - Denuncia a Actualizar
	 * @return Denuncia Actualizada
	 * @see {@link DenunciaService#updateDenuncia(Denuncia)}
	 */
	@PutMapping
	public Reporte updateReporte(@RequestBody Reporte reporte) {
		return reporteService.updateDenuncia(reporte);
	}
}
