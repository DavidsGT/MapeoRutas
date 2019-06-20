package com.webServices.rutas.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public void timeBetweenStopsAlternative() throws IOException{
		//TODO crear index para mayor velocidad de consulta
		File file = new File("prob.txt");
		  
		//Create the file
		if (file.createNewFile())
		{
		    System.out.println("File is created!");
		} else {
		    System.out.println("File already exists.");
		}
		 
		//Write Content
		FileWriter writer = new FileWriter(file);
		writer.write("Antes de buscar");
		writer.close();
		List<HistorialEstadoBus> allHistorialEstadoBus = getHistorialDelDia();
		FileWriter writer2 = new FileWriter(file);
		writer2.write("despues de buscar");
		writer2.close();
        //Recorrer los historiales del los buses
        for(HistorialEstadoBus oneHistorial : allHistorialEstadoBus) {
        	System.out.println("Para el bus: " + oneHistorial.getPlaca());
        	List<EstadoBus> listEstadosDelHistorial = oneHistorial.getListaEstados1();
        	listEstadosDelHistorial.addAll(oneHistorial.getListaEstados2());
        	listEstadosDelHistorial.addAll(oneHistorial.getListaEstados3());
        	//Guardar temporalmente para poder evaluar por SpatialView
        	//buscar las paradas pertenecientes a la linea que esta haciendo el recorrido
        	List<Parada> paradasByLinea = (List<Parada>) paradaRepository.findAllByLinea(String.valueOf(oneHistorial.getLinea()));
        	//Buscar TimeCoontrol para esta linea
        	TimeControlParada timeControlParada = timeControlParadaRepository.findByLinea(String.valueOf(oneHistorial.getLinea()));
        	if(timeControlParada == null) {
        		timeControlParada = new TimeControlParada(String.valueOf(oneHistorial.getLinea()),paradasByLinea);
        	}
        	//Recorro las paradas
        	for(int i = 0; i <= paradasByLinea.size()-1; i++) {
        		Parada p = paradasByLinea.get(i);
        		//por cada parada pregunto si existen buses cercanos a menos de 3 metros a la redonda
        		List<EstadoBusTemporal> busesCercanos = new ArrayList<>();
        		for(i=1;i<=3;i++)
        		{
        			 busesCercanos.addAll(findBusesCercanos(p, oneHistorial.getId(),"listaEstados"+String.valueOf(i)));
        		}
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
	private List<HistorialEstadoBus> getHistorialDelDia() throws FileNotFoundException, UnsupportedEncodingException {
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
	private List<EstadoBusTemporal> findBusesCercanos(Parada p,String idHistorial,String listado) {
		double lat = p.getCoordenada().getY();
		double lon = p.getCoordenada().getX();
		GeoLocation loc = new GeoLocation(lat,lon);
		List<GeoLocation> SW_NE_LOC = loc.bounding_locations(GlobalVariables.distanceMaxBusesToParada);
		String meridian180condition = (SW_NE_LOC.get(0).getRad_lon() > SW_NE_LOC.get(1).getRad_lon()) ? " OR " : " AND ";
		return historialEstadoBusRepository.findByListaEstadosInPosicionWithIn(listado,meridian180condition, loc.getDeg_lat(),  
																				loc.getDeg_lon(), SW_NE_LOC.get(0).getRad_lat(),  
																				SW_NE_LOC.get(0).getRad_lon(), SW_NE_LOC.get(1).getRad_lat(),
																				SW_NE_LOC.get(1).getRad_lon(), GlobalVariables.distanceMaxBusesToParada,idHistorial);
	}
}