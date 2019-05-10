package com.webServices.rutas.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoPage;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.HistorialEstadoBus;
import com.webServices.rutas.model.Parada;
import com.webServices.rutas.repository.EstadoBusTemporalRepository;
import com.webServices.rutas.repository.HistorialEstadoBusRepository;
import com.webServices.rutas.repository.ParadaRepository;
@Service
public class NightCalculation {
	@Autowired
	HistorialEstadoBusRepository historialEstadoBusRepository;
	@Autowired
	EstadoBusTemporalRepository estadoBusTemporalRepository;
	@Autowired
	private ParadaRepository paradaRepository;
	public NightCalculation() {
		super();
	}
	public void timeBetweenStops(){
		/*Obtengo todos los circuitos de los buses del dia actual*/
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);   
        now.set(Calendar.HOUR_OF_DAY, 0);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = df.format(now.getTime());
        todayAsString = "2019-05-08";
        List<HistorialEstadoBus> allHistorialEstadoBus = historialEstadoBusRepository.findByCreadoEn(todayAsString);
        for(HistorialEstadoBus oneHEB : allHistorialEstadoBus) {
        	List<EstadoBusTemporal> temp = (List<EstadoBusTemporal>) estadoBusTemporalRepository.saveAll(oneHEB.getListaEstadosTemporal());
        	List<Parada> paradasByLinea = (List<Parada>) paradaRepository.findAllByLinea(String.valueOf(oneHEB.getListaEstados().get(0).getLinea()));
        	for(int i = 0; i <= paradasByLinea.size()-1; i++) {
        		Parada p = paradasByLinea.get(i);
    			Circle circle = new Circle(p.getCoordenada(),new Distance(0.4, Metrics.KILOMETERS));
    			List<EstadoBusTemporal> busesCercanos = estadoBusTemporalRepository.findByPosicionActualWithin(circle);
    			Parada siguienteParada = paradasByLinea.get(i+1);
    			for(EstadoBusTemporal e : busesCercanos) {
    				int idxw = e.getIdx();
    				List<EstadoBus> listEstados = oneHEB.getListaEstados();
    				double distanciaInicial = siguienteParada.distance(e.getPosicionActual(), "K");
    				for(int j = idxw + 1;j <=listEstados.size();j++) {
    					Double auxDistance = siguienteParada.distance(listEstados.get(j).getPosicionActual(), "K");
    				} 				
    			}
    			busesCercanos.forEach(System.out::println);
        	}
    		estadoBusTemporalRepository.deleteAll();
        }
	}
}