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
        //Recorrer los resultados para evaluar puntos
        for(HistorialEstadoBus oneHEB : allHistorialEstadoBus) {
        	//Guardar para poder evaluar por SpatialView
        	List<EstadoBusTemporal> temp = (List<EstadoBusTemporal>) estadoBusTemporalRepository.saveAll(oneHEB.getListaEstadosTemporal());
        	//buscar las paradas pertenecientes a la linea que esta haciendo el recorrido
        	List<Parada> paradasByLinea = (List<Parada>) paradaRepository.findAllByLinea(String.valueOf(oneHEB.getListaEstados().get(0).getLinea()));
        	//recorro las paradas
        	for(int i = 0; i <= paradasByLinea.size()-1; i++) {
        		Parada p = paradasByLinea.get(i);
        		//por cada parada pregunto si existen buses cercanos a menos de 4 metros
    			Circle circle = new Circle(p.getCoordenada(),new Distance(0.4, Metrics.KILOMETERS));
    			List<EstadoBusTemporal> busesCercanos = estadoBusTemporalRepository.findByPosicionActualWithin(circle);
    			//obtengo la siguiente parada para comenzar a recorrer cada punto del historial hasta encontrar el menor
    			Parada siguienteParada;
    			if((i+1) >= paradasByLinea.size()){
				  //index not exists parada
    				siguienteParada = paradasByLinea.get(0);
				}else{
				 // index exists parada
					siguienteParada = paradasByLinea.get(i+1);
				}
    			//Si encuentra multiples buses cercanos a la parada recorre
    			for(EstadoBusTemporal e : busesCercanos) {
    				//obtiene su index para comenzar a evaluar de alli en adelante
    				int idxw = e.getIdx();
    				List<EstadoBus> listEstados = oneHEB.getListaEstados();
    				//Distancia inicial a la siguiente parada
    				double menorDistancia = siguienteParada.distance(e.getPosicionActual(), "K");
    				System.out.println("Distancia Inicial: "+menorDistancia);
    				double otraDistancia;
    				//comienza a recorrer desde el index registrado
    				for(int j = idxw+1;j <=listEstados.size()-1;j++) {
    					//consultar siguiente distancia
    					otraDistancia = siguienteParada.distance(listEstados.get(j).getPosicionActual(), "K");
    					if(otraDistancia < menorDistancia ) {
    						menorDistancia = otraDistancia;
    					}else {
    						//RESTAR E MENOS EL MAS CERCANO A LA PARADA SIGUIENTE
    						long diffInMillies = Math.abs(e.getCreationDate().getTime() - listEstados.get(j).getCreationDate().getTime());
    					    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    						System.out.println("La distancia mayor a esta era: " + otraDistancia);
    						System.out.println("La menor distanciaes: " + menorDistancia);
    						break;
    					}
    				}
    			}
        	}
    		estadoBusTemporalRepository.deleteAll();
        }
	}
}