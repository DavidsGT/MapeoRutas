package com.webServices.rutas.services;

import java.io.IOException;
import java.util.Calendar;
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

@Service
public class BusService {
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
		return busRepository.findByIdAndEstadoIsTrue(placa);
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
	public Iterable<EstadoBus> getHistorialEstadoBusAllByPlacaByFecha(String placa,Calendar fecha){
		return historialEstadoBusRepository.findById(fecha+"::"+placa).get().getListaEstados();
	}
	
	/**
	 * Obtener el ultimo estado del bus
	 * @param placa - Placa del Bus a obtener el estado
	 * @return	Estado actual del Bus
	 */
	public EstadoBus getEstadoActualBus(String placa) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("ECT"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, -22);
		int idx = historialEstadoBusRepository.findById(now+"::"+placa).get().getListaEstados().size();
		return historialEstadoBusRepository.findById(placa).get().getListaEstados().get(idx-1);
	}
	
	/**
	 * Añade un nuevo estado al bus en un respectivo Dia
	 * @param estadoBus - Estado del bus a guardar
	 * @param placa - Placa del bus
	 */
	public void updateEstadoBus(EstadoBus estadoBus,String placa) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("ECT"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, -22);
        historialEstadoBusRepository.findById(now+"::"+placa);
		HistorialEstadoBus c = historialEstadoBusRepository.findById(placa).get();
		c.getListaEstados().add(estadoBus);
		historialEstadoBusRepository.save(c);
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
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("ECT"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, -22);
        HistorialEstadoBus c = historialEstadoBusRepository.findById(now+"::"+placa).get();
		Gson mapper = new Gson();
		EstadoBus obj = mapper.fromJson(valor, EstadoBus.class);
		c.getListaEstados().add(obj);
		historialEstadoBusRepository.save(c);
	}
	
	/**
	 * Añade un nuevo estado al bus en un respectivo dia pero entregando valores de representan el estado seguido de comas
	 * @param valor - datos del Estado seguido de comas
	 * @param placa - Placa del Bus
	 * @deprecated Metodo no recomendable favor ver {@link BusService#updateEstadoBus(EstadoBus, String)}
	 * @see {@link BusService#updateEstadoBus(EstadoBus, String)}
	 */
	public void updateEstadoBusGETAlternative(String valor, String placa) {
		String[] attrib = valor.split(",");
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("ECT"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, -22);
        HistorialEstadoBus c = historialEstadoBusRepository.findById(now+"::"+placa).get();
		EstadoBus obj = new EstadoBus(	Integer.parseInt(attrib[0]),
										Integer.parseInt(attrib[1]),
										new Point(Double.parseDouble(attrib[2]),
										Double.parseDouble(attrib[3])),
										Boolean.parseBoolean(attrib[4]),
										Integer.parseInt(attrib[5]));
		c.getListaEstados().add(obj);
		historialEstadoBusRepository.save(c);
	}
}