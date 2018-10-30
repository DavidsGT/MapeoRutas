package com.webServices.rutas.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webServices.rutas.model.Parada;
import com.webServices.rutas.model.Ruta;
import com.webServices.rutas.repository.ParadaRepository;
import com.webServices.rutas.repository.RutaRepository;
import com.webServices.rutas.util.Gpx;
@Service
public class RutaService {
	@Autowired
	private ParadaRepository paradaRepository;
	@Autowired
	private RutaRepository rutaModelRepository;
	public Ruta getRutaModel(String id) {
		return null;
	}
	@SuppressWarnings("unchecked")
	public Ruta addRutaWithGPX(MultipartFile file,String linea) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		Map<String, Object> x = Gpx.decodeGPX(convFile);
		List<Point> ruta = (List<Point>) x.get("ruta");
		List<Parada> paradas = (List<Parada>) x.get("paradas");
		List<String> paradaId = new ArrayList<String>();
		for(Parada p: paradaRepository.saveAll(paradas))
			paradaId.add(p.getId());
		Ruta rutaAdd = new Ruta(linea,ruta, paradaId);
		return rutaAdd;
	}
	public Ruta getRutaModelLinea(String linea) {
		return rutaModelRepository.findById(linea).get();
	}
	public Iterable<Ruta> getAllRutaModels(){
		return rutaModelRepository.findAll();
	}
	public void addRutaModel(Ruta rutaModel) {
		rutaModelRepository.save(rutaModel);
	}
	public void updateRutaModel(String id,Ruta rutaModel) {
	}
	public void deleteRutaModel(String id) {
		rutaModelRepository.deleteById(id);
	}
	public void deleteAllRutaModel() {
		rutaModelRepository.deleteAll();
	}
}