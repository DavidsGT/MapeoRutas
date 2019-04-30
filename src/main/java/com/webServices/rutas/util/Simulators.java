package com.webServices.rutas.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.Ruta;
import com.webServices.rutas.repository.RutaRepository;
import com.webServices.rutas.services.BusService;
@Service
public class Simulators {
	@Autowired
	private RutaRepository rutaRepository;
	@Autowired
	private BusService busService;
	public void runLinea7() throws InterruptedException {
		Ruta rs = rutaRepository.findById("7").get();
		Random aleatorio = new Random(System.currentTimeMillis());
		int count = 0;
		while(true) {
			Thread.sleep(10*1000);
			System.out.println("count = " + count + " ,total de puntos = " + rs.getListasPuntos().size());
			EstadoBus b = new EstadoBus(aleatorio.nextInt(80), aleatorio.nextInt(35), rs.getListasPuntos().get(count), Math.random() < 0.5, 7); 
			busService.updateEstadoBus(b, "ABC1234");
			count = count + 5;
			if(count >= rs.getListasPuntos().size()) {
				count = 0;
			}
		}
	}
}
