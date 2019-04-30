package com.webServices.rutas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.aop.target.SimpleBeanTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.repository.query.SpatialViewQueryCreator;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.view.SpatialViewQuery;
import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.HistorialEstadoBus;
import com.webServices.rutas.model.Parada;
import com.webServices.rutas.repository.EstadoBusTemporalRepository;
import com.webServices.rutas.repository.HistorialEstadoBusRepository;
import com.webServices.rutas.repository.ParadaRepository;
import com.webServices.rutas.repository.RutaRepository;
@Service
public class NightCalculation {
	RedisTemplate<Date, EstadoBus> operations = new RedisTemplate<Date, EstadoBus>();
	@Autowired
	HistorialEstadoBusRepository historialEstadoBusRepository;
	@Autowired
	EstadoBusTemporalRepository estadoBusTemporalRepository;
	@Autowired
	private RutaRepository rutaRepository;
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
        todayAsString = "2019-03-08";
        List<HistorialEstadoBus> allHistorialEstadoBus = historialEstadoBusRepository.findByCreadoEn(todayAsString);
        
        for(HistorialEstadoBus oneHEB : allHistorialEstadoBus) {
        	estadoBusTemporalRepository.saveAll(oneHEB.getListaEstadosTemporal());
        	Iterable<Parada> paradasByLinea = paradaRepository.findAllByLinea(String.valueOf(oneHEB.getListaEstados().get(0).getLinea()));
        	for(Parada p : paradasByLinea) {
        		//TODO preguntar a Quinde si era hectometros o decametros
    			Circle circle = new Circle(p.getCoordenada(),new Distance(333, Metrics.KILOMETERS));
    			List<EstadoBusTemporal> busesCercanos = (List<EstadoBusTemporal>) estadoBusTemporalRepository.findByPosicionActualWithin(circle);
    			busesCercanos.sort(Comparator.comparing(EstadoBusTemporal::getCreationDate));
    			System.out.println(busesCercanos + " con la parada en los puntos.. " + p.getCoordenada());
        	}
    		estadoBusTemporalRepository.deleteAll();
        }
		/*Circle circle = new Circle(new Point(13.583333, 37.316667), //
				new Distance(100, Metrics.KILOMETERS));
		GeoResults<GeoLocation<String>> result = geoOperations.radius("Sicily", circle);
		System.out.println(result.toString());
		
		//assertThat(result).hasSize(2).extracting("content.name").contains("Arigento", "Palermo");
        
		Point pointParada = new Point(-80.8556258,-2.2245513);
		int distanceMax = 0;
		List<HistorialEstadoBus> par = new ArrayList<>();
		/*while(distanceMax == 34) {
			SpatialViewQueryCreator x;
			SpatialViewQuery q = SpatialViewQuery.from("spatial", "by_location")
				    .startRange(JsonArray.from(0, -90, null))
				    .endRange(JsonArray.from(180, 90, null));
			distanceMax = distanceMax + 2;
			
			Circle circle = new Circle(pointParada,new Distance(99999999, Metrics.KILOMETERS));
			System.out.println(par.size());
			if(par.size() == 2) {
				break;
			}
		}
		if(par.size() > 0) {
			System.out.println(par.toString());
		}else {
			System.out.println("No hay Buses Cercanos");
		}
		
		
        //TODO Esta linea esta solo para pruebas trae uno ya existende en la bd.
        
        /************/
        /*Recorrer los circuitos para trabajar con un solo circuito a la vez*/
        /*for(HistorialEstadoBus oneHistorialEstadoBus : allHistorialEstadoBus) {
        	//Se asume que la linea del bus no cambia en el dia
        	//Por lo tanto traer las paradas que pertencen a ese circuito
        	List<String> idsParadas = rutaRepository.findById(String.valueOf(oneHistorialEstadoBus.getListaEstados().get(0).getLinea())).get().getListasParadas();
        	List<Parada> paradasOfLinea = (List<Parada>) paradaRepository.findAllById(idsParadas);
        }*/
        //Wed Apr 17 00:00:00 COT 2019
	}
	
	
}