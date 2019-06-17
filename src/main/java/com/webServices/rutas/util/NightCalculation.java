package com.webServices.rutas.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.GeoLocation;
import com.webServices.rutas.model.GlobalVariables;
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
	@Scheduled(cron=GlobalVariables.timeScheduled, zone="America/Guayaquil")
	public void timeBetweenStopsAlternative(){
		//TODO crear index para mayor velocidad de consulta
		List<HistorialEstadoBus> allHistorialEstadoBus = getHistorialDelDia();
        //Recorrer los historiales del los buses
        for(HistorialEstadoBus oneHistorial : allHistorialEstadoBus) {
        	System.out.println("Para el bus: " + oneHistorial.getPlaca());
        	List<EstadoBus> listEstadosDelHistorial = oneHistorial.getListaEstados();
        	//Guardar temporalmente para poder evaluar por SpatialView
        	//buscar las paradas pertenecientes a la linea que esta haciendo el recorrido
        	List<Parada> paradasByLinea = (List<Parada>) paradaRepository.findAllByLinea(String.valueOf(oneHistorial.getListaEstados().get(0).getLinea()));
        	//Buscar TimeCoontrol para esta linea
        	TimeControlParada timeControlParada = timeControlParadaRepository.findByLinea(String.valueOf(oneHistorial.getListaEstados().get(0).getLinea()));
        	if(timeControlParada == null) {
        		timeControlParada = new TimeControlParada(String.valueOf(oneHistorial.getListaEstados().get(0).getLinea()),paradasByLinea);
        	}
        	//Recorro las paradas
        	for(int i = 0; i <= paradasByLinea.size()-1; i++) {
        		Parada p = paradasByLinea.get(i);
        		//por cada parada pregunto si existen buses cercanos a menos de 3 metros a la redonda
    			List<EstadoBusTemporal> busesCercanos = findBusesCercanos(p, oneHistorial.getId());
    			System.out.println("Cantidad de buses: "+busesCercanos.size());
    			//obtengo la siguiente parada para comenzar a recorrer cada punto del historial hasta encontrar el menor
    			Parada siguienteParada;
    			if((i+1) >= paradasByLinea.size())
    				siguienteParada = paradasByLinea.get(0);
				else
					siguienteParada = paradasByLinea.get(i+1);
    			//Si encuentra multiples buses cercanos a la parada recorre
    			for(EstadoBusTemporal e : busesCercanos) {
    				//obtiene su index para comenzar a evaluar de alli en adelante
    				int idxw = e.getIdx(); 
    				//Distancia inicial a la siguiente parada
    				double menorDistancia = siguienteParada.distance(e.getPosicionActual(), "M");
    				EstadoBus estadoBusMenorDistancia = listEstadosDelHistorial.get(idxw);
    				//System.out.println("Distancia Inicial: "+menorDistancia);
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
    					    Long diff = diffInMillies/1000;//segundos
    					    //Buscar si ya existen en timeControlParada
    					    if(diff != 0) {
    					    	//TODO Falta comprobar si ya existe un BetweenParada con mismo ip1 y idP2
    					    	timeControlParada = timeControlParada.buscarParada1AndParada2(p.getId(),siguienteParada.getId(),diff);
    					    }
    						break;
    					}
    				}
    			}
        	}
    		timeControlParada = timeControlParadaRepository.save(timeControlParada);
        }
	}
	private List<HistorialEstadoBus> getHistorialDelDia() {
        String todayAsString;
        if(GlobalVariables.fechaNightCalculation.equals("")) {
        	Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            todayAsString = df.format(now.getTime());
        }else {
        	todayAsString = GlobalVariables.fechaNightCalculation;
        }
        return historialEstadoBusRepository.findByCreadoEn(todayAsString);
	}
	private List<EstadoBusTemporal> findBusesCercanos(Parada p,String idHistorial) {
		double lat = p.getCoordenada().getY();
		double lon = p.getCoordenada().getX();
		GeoLocation loc = new GeoLocation(lat,lon);
		List<GeoLocation> SW_NE_LOC = loc.bounding_locations(GlobalVariables.distanceMaxBusesToParada);
		String meridian180condition = (SW_NE_LOC.get(0).getRad_lon() > SW_NE_LOC.get(1).getRad_lon()) ? " OR " : " AND ";
		return historialEstadoBusRepository.findByListaEstadosInPosicionWithIn(meridian180condition, loc.getDeg_lat(),  
																				loc.getDeg_lon(), SW_NE_LOC.get(0).getRad_lat(),  
																				SW_NE_LOC.get(0).getRad_lon(), SW_NE_LOC.get(1).getRad_lat(),
																				SW_NE_LOC.get(1).getRad_lon(), GlobalVariables.distanceMaxBusesToParada,idHistorial);
	}
}