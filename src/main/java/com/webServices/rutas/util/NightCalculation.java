package com.webServices.rutas.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.HistorialEstadoBus;
import com.webServices.rutas.model.Parada;
import com.webServices.rutas.model.TimeControlParada;
import com.webServices.rutas.repository.EstadoBusTemporalRepository;
import com.webServices.rutas.repository.HistorialEstadoBusRepository;
import com.webServices.rutas.repository.ParadaRepository;
import com.webServices.rutas.repository.TimeControlParadaRepository;
@Component
public class NightCalculation {
	@Autowired
	HistorialEstadoBusRepository historialEstadoBusRepository;
	@Autowired
	EstadoBusTemporalRepository estadoBusTemporalRepository;
	@Autowired
	TimeControlParadaRepository timeControlParadaRepository;
	@Autowired
	private ParadaRepository paradaRepository;
	@Scheduled(cron="0 0 0 * * ?", zone="America/Guayaquil")
	public void timeBetweenStops(){
		List<HistorialEstadoBus> allHistorialEstadoBus = getHistorialDelDia();
        //Recorrer los historiales del los buses
        for(HistorialEstadoBus oneHistorial : allHistorialEstadoBus) {
        	System.out.println("Para el bus: " + oneHistorial.getPlaca());
        	List<EstadoBus> listEstadosDelHistorial = oneHistorial.getListaEstados();
        	//Guardar temporalmente para poder evaluar por SpatialView
        	estadoBusTemporalRepository.saveAll(oneHistorial.getListaEstadosTemporal());
        	//buscar las paradas pertenecientes a la linea que esta haciendo el recorrido
        	List<Parada> paradasByLinea = (List<Parada>) paradaRepository.findAllByLinea(String.valueOf(oneHistorial.getListaEstados().get(0).getLinea()));
        	//Buscar TimeCoontrol para esta linea
        	TimeControlParada timeControlParada = timeControlParadaRepository.findByLinea(String.valueOf(oneHistorial.getListaEstados().get(0).getLinea()));
        	if(timeControlParada == null) {
        		timeControlParada = new TimeControlParada(String.valueOf(oneHistorial.getListaEstados().get(0).getLinea()));
        	}
        	//Recorro las paradas
        	for(int i = 0; i <= paradasByLinea.size()-1; i++) {
        		Parada p = paradasByLinea.get(i);
        		//por cada parada pregunto si existen buses cercanos a menos de 3 metros a la redonda
    			Circle circle = new Circle(p.getCoordenada(),new Distance(0.3, Metrics.KILOMETERS));
    			List<EstadoBusTemporal> busesCercanos = estadoBusTemporalRepository.findByPosicionActualWithin(circle);
    			//obtengo la siguiente parada para comenzar a recorrer cada punto del historial hasta encontrar el menor
    			Parada siguienteParada;
    			if((i+1) >= paradasByLinea.size()){
    				siguienteParada = paradasByLinea.get(0);
				}else{
					siguienteParada = paradasByLinea.get(i+1);
				}
    			//Si encuentra multiples buses cercanos a la parada recorre
    			for(EstadoBusTemporal e : busesCercanos) {
    				//obtiene su index para comenzar a evaluar de alli en adelante
    				int idxw = e.getIdx(); 
    				//Distancia inicial a la siguiente parada
    				double menorDistancia = siguienteParada.distance(e.getPosicionActual(), "M");
    				EstadoBus estadoBusMenorDistancia = listEstadosDelHistorial.get(idxw);
    				System.out.println("Distancia Inicial: "+menorDistancia);
    				double otraDistancia;
    				//comienza a recorrer desde el index registrado
    				for(int j = idxw+1;j <=listEstadosDelHistorial.size()-1;j++) {
    					//consultar siguiente distancia en metros
    					otraDistancia = siguienteParada.distance(listEstadosDelHistorial.get(j).getPosicionActual(), "M");
    					if(otraDistancia < menorDistancia ) {
    						menorDistancia = otraDistancia;
    						estadoBusMenorDistancia = listEstadosDelHistorial.get(j);
    					}else {
    						Long diffInMillies = Math.abs(e.getCreationDate().getTime() - listEstadosDelHistorial.get(j).getCreationDate().getTime());
    					    Long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    					    //Buscar si ya existen en timeControlParada
    					    if(diff != 0) {
    					    	timeControlParada.buscarParada1AndParada2(p.getId(),siguienteParada.getId(),diff);
    					    }
    						//RESTAR E MENOS EL MAS CERCANO A LA PARADA SIGUIENTE
    						System.out.println("La distancia mayor a esta era: " + otraDistancia);
    						System.out.println("La menor distancia es: " + menorDistancia);
    						System.out.println("la diferencia de tiempo es " + diff + " segundos.");
    						System.out.println("El estado crecano a la parada es: " + estadoBusMenorDistancia.toString());
    						break;
    					}
    				}
    			}
        	}
        	System.out.println(timeControlParadaRepository.save(timeControlParada).toString());
    		estadoBusTemporalRepository.deleteAll();
        }
	}
	private List<HistorialEstadoBus> getHistorialDelDia() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = df.format(now.getTime());
        todayAsString = "2019-05-17";
        return historialEstadoBusRepository.findByCreadoEn(todayAsString);
	}
}