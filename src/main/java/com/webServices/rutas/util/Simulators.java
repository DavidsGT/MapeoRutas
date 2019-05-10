package com.webServices.rutas.util;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

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
	public void startSimulador(int linea,String placa) throws InterruptedException {
		Ruta rs = rutaRepository.findById(String.valueOf(linea)).get();
		int count = 0;
		Random alea1 = new Random(System.currentTimeMillis());
		Random alea2 = new Random(System.currentTimeMillis());
		Random alea3 = new Random(System.currentTimeMillis());
		boolean ban = true;
		while(ban) {
			Thread.sleep(10*1000);
			System.out.println("CONTADOR: " + count);
			EstadoBus b = new EstadoBus(alea1.nextInt(80), alea3.nextInt(35), rs.getListasPuntos().get(count), Math.random() < 0.5, linea); 
			busService.updateEstadoBus(b, placa);
			count = count + alea2.nextInt(5);
			if(count >= rs.getListasPuntos().size()) {
				count = 0;
			}
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
			if (now.get(Calendar.HOUR_OF_DAY) == 20) {
				ban = false;
			}
		}
	}
}