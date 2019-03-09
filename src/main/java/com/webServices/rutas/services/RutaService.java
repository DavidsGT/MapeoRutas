package com.webServices.rutas.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.webServices.rutas.exception.FileStorageException;
import com.webServices.rutas.model.FileStorageProperties;
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
	private RutaRepository rutaRepository;
	
	private final Path fileStorageLocation;
	public Ruta getRuta(String linea) {
		return rutaRepository.findById(linea).get();
	}
	public List<Ruta> getAllRuta(){
		return (List<Ruta>) rutaRepository.findByEstadoIsTrue();
	}
	public Ruta addRuta(Ruta ruta) {
		return rutaRepository.save(ruta);
	}
	public Ruta updateRuta(Ruta ruta) {
		return rutaRepository.save(ruta);
	}
	public void deleteRuta(String id) {
		Ruta c = rutaRepository.findById(id).get();
		c.setEstado(false);
		rutaRepository.save(c);
	}
	@Autowired
    public RutaService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
	@SuppressWarnings("unchecked")
	public Ruta addRutaWithGPX(MultipartFile file, String linea) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
        fos.close();
        Map<String, Object> x = Gpx.decodeGPX(convFile);
		List<Point> ruta = (List<Point>) x.get("ruta");
		List<Parada> paradas = (List<Parada>) x.get("parada");
		List<String> paradaId = new ArrayList<String>();
		for(Parada p: paradaRepository.saveAll(paradas))
			paradaId.add(p.getId());
		Ruta rutaAdd = new Ruta(linea,ruta, paradaId);
		return rutaRepository.save(rutaAdd);
	}
	public List<Ruta> getAllRutaIgnoreEstado() {
		return (List<Ruta>) rutaRepository.findAll();
	}
}