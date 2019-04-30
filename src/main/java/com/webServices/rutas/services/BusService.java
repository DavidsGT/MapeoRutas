package com.webServices.rutas.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonParseException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.webServices.rutas.model.Bus;
import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.HistorialEstadoBus;
import com.webServices.rutas.repository.BusRepository;
import com.webServices.rutas.repository.HistorialEstadoBusRepository;
import com.webServices.rutas.util.NightCalculation;
import com.webServices.rutas.util.Simulators;

@Service
public class BusService {
	@Autowired
	private NightCalculation nightCalculation;
	@Autowired
	Simulators s;
	/**
	 * 
	 */
	@Autowired
	private HistorialEstadoBusRepository historialEstadoBusRepository;
	
	@Autowired
	private BusRepository busRepository;
	
	/**
	 * Obtener datos de un Bus entregando su respectiva placa.
	 * @param placa - Placa del Bus que desee obtener los datos
	 * @return Bus
	 */
	public Bus getBus(String placa) {
		return busRepository.findByPlacaAndEstadoIsTrue(placa);
	}
	
	/**
	 * Obtener datos de un Bus entregando su respectiva placa.
	 * Ignorando su estado eliminado
	 * @param placa - Placa del Bus que desee obtener los datos
	 * @return Bus
	 */
	public Bus getBusIgnoreEstado(String placa) {
		return busRepository.findById(placa).get();
	}
	
	/**
	 * Obtener lista de Buses
	 * @return Lista de Buses
	 */
	public List<Bus> getAllBus(){
		return (List<Bus>) busRepository.findByEstadoIsTrue(); 
	}
	
	/**
	 * Obtener Lista de Buses Ignorando su estado Eliminado
	 * @return
	 */
	public List<Bus> getAllBusIgnoreEstado(){
		return (List<Bus>) busRepository.findAll();
	}
	
	/**
	 * Agregar Bus
	 * @param bus
	 * @return Bus agregado
	 */
	public Bus addBus(Bus bus) {
		return busRepository.save(bus);
	}
	
	/**
	 * Actualiza datos de un Bus
	 * @return Bus actualizado
	 */
	public Bus updateBus(Bus bus) {
		return busRepository.save(bus);
	}
	
	/**
	 * Elimina un Bus
	 * @param placa - Placa del bus a Eliminar
	 */
	public void deleteBus(String placa) {
		Bus c = busRepository.findById(placa).get();
		c.setEstado(false);
		busRepository.save(c);
	}
	
	/**
	 * Obtener Listado de Buses pertenecientes a una cooperativa
	 * @param idCooperativa - Id de la Cooperativa
	 * @return Listado de Buses de una Coooperativa
	 */
	public Iterable<Bus> getBusesByIdCooperativa(String idCooperativa){
		return busRepository.findByIdCooperativaAndEstadoIsTrue(idCooperativa);
	}
	
	/**
	 * Retorna una lista de Estados que ha tenido el bus en el transcurso del dia.
	 * @param placa - Plava del Bus a consultar su estado
	 * @param fecha - fecha del dia que se desee consultar
	 * @return
	 */
	public Iterable<EstadoBus> getHistorialEstadoBusAllByPlacaByFecha(String placa,Date fecha){
		return historialEstadoBusRepository.findById(fecha+"::"+placa).get().getListaEstados();
	}
	
	/**
	 * Obtener el ultimo estado del bus
	 * @param placa - Placa del Bus a obtener el estado
	 * @return	Estado actual del Bus
	 */
	public EstadoBus getEstadoActualBus(String placa) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
		int idx = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get().getListaEstados().size();
		return historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get().getListaEstados().get(idx-1);
	}
	
	/**
	 * Añade un nuevo estado al bus en un respectivo Dia
	 * @param estadoBus - Estado del bus a guardar
	 * @param placa - Placa del bus
	 */
	public void updateEstadoBus(EstadoBus estadoBus,String placa) {
		HistorialEstadoBus h;
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        if(historialEstadoBusRepository.existsById(now.getTime()+"::"+placa)) {
        	h = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get();
        	h.getListaEstados().add(estadoBus);
        }else {
        	List<EstadoBus> eb = new ArrayList<>();
        	eb.add(estadoBus);
        	h = new HistorialEstadoBus();
        	h.setPlaca(placa);
        	h.setListaEstados(eb);
        }
		historialEstadoBusRepository.save(h);
	}
	
	/**
	 * Añade un nuevo estado al bus en un respectivo dia pero entregando un cadena Json como estadoBus
	 * @param valor - Cadena Json del Estado Bus
	 * @param placa - Placa del bus
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @deprecated Metodo no recomendable favor ver {@link BusService#updateEstadoBus(EstadoBus, String)}
	 * @see {@link BusService#updateEstadoBus(EstadoBus, String)}
	 */
	public void updateEstadoBusGET(String valor, String placa) throws JsonParseException, JsonMappingException, IOException {
		HistorialEstadoBus h;
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        Gson mapper = new Gson();
		EstadoBus obj = mapper.fromJson(valor, EstadoBus.class);
        if(historialEstadoBusRepository.existsById(now.getTime()+"::"+placa)) {
        	h = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get();
        	h.getListaEstados().add(obj);
        }else {
        	List<EstadoBus> eb = new ArrayList<>();
        	eb.add(obj);
        	h = new HistorialEstadoBus(placa,eb);
        }
		historialEstadoBusRepository.save(h);
	}
	
	/**
	 * Añade un nuevo estado al bus en un respectivo dia pero entregando valores de representan el estado seguido de comas
	 * @param valor - datos del Estado seguido de comas
	 * @param placa - Placa del Bus
	 * @deprecated Metodo no recomendable favor ver {@link BusService#updateEstadoBus(EstadoBus, String)}
	 * @see {@link BusService#updateEstadoBus(EstadoBus, String)}
	 */
	public void updateEstadoBusGETAlternative(String valor, String placa) {
		HistorialEstadoBus h;
		String[] attrib = valor.split(",");
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
		EstadoBus obj = new EstadoBus(	Integer.parseInt(attrib[0]),
										Integer.parseInt(attrib[1]),
										new Point(Double.parseDouble(attrib[2]),
										Double.parseDouble(attrib[3])),
										Boolean.parseBoolean(attrib[4]),
										Integer.parseInt(attrib[5]));
		if(historialEstadoBusRepository.existsById(now.getTime()+"::"+placa)) {
        	h = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get();
        	h.getListaEstados().add(obj);
        }else {
        	List<EstadoBus> eb = new ArrayList<>();
        	eb.add(obj);
        	h = new HistorialEstadoBus();
        	h.setPlaca(placa);
        	h.setListaEstados(eb);
        }
		historialEstadoBusRepository.save(h);
	}
	
	//Obtener trafico de buses online
	public List<EstadoBus> getTraficBus() throws ParseException, InterruptedException {
		
		//s.runLinea7();
		nightCalculation.timeBetweenStops();
		return null;
	}

	public long getCalculateTimeToStop(String idParada,String placaBus) {
		try {
			//
			String time1 = "16:00:00";
			String time2 = "19:00:00";
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date date1 = format.parse(time1);
			Date date2 = format.parse(time2);
			long difference = date2.getTime() - date1.getTime(); 
			return difference;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	public List<EstadoBus> getEstadoActualBusByLinea(String linea) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);   
        now.set(Calendar.HOUR_OF_DAY, 0);
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(now.getTime());
        
        List<EstadoBus> list = historialEstadoBusRepository.findLastEstadoBusByLinea("2019-03-08", linea);
		return list;
	}
}