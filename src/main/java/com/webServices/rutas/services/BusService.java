package com.webServices.rutas.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonParseException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.webServices.rutas.model.BetweenParada;
import com.webServices.rutas.model.Bus;
import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.GlobalVariables;
import com.webServices.rutas.model.HistorialEstadoBus;
import com.webServices.rutas.model.Parada;
import com.webServices.rutas.model.TimeControlParada;
import com.webServices.rutas.repository.BusRepository;
import com.webServices.rutas.repository.HistorialEstadoBusRepository;
import com.webServices.rutas.repository.TimeControlParadaRepository;
import com.webServices.rutas.util.Simulators;

@Service
public class BusService {
	@Autowired
	private ParadaService paradaService;
	@Autowired
	private Simulators s;
	@Autowired
	private TimeControlParadaRepository timeControlParadaRepository;
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
	public List<Iterable<EstadoBus>> getHistorialEstadoBusAllByPlacaByFecha(String placa,Date fecha){
		HistorialEstadoBus h = historialEstadoBusRepository.findById(fecha+"::"+placa).get();
		List<Iterable<EstadoBus>> eb = new ArrayList<Iterable<EstadoBus>>(Arrays.asList(h.getListaEstados1(), h.getListaEstados2(), h.getListaEstados3()));
		return eb;
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
        HistorialEstadoBus h = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get();
        EstadoBus lastElement = Iterables.getLast(h.getListaEstados3(), Iterables.getLast(h.getListaEstados2(), Iterables.getLast(h.getListaEstados1(), null)));
		return lastElement;
	}

	/**
	 * Añade un nuevo estado al bus en un respectivo Dia
	 * @param estadoBus - Estado del bus a guardar
	 * @param placa - Placa del bus
	 */
	public void updateEstadoBus(EstadoBus estadoBus,String placa,int linea) {
		HistorialEstadoBus h;
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        if(historialEstadoBusRepository.existsById(now.getTime()+"::"+placa)) {
        	h = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get();
        	if(h.getListaEstados1().size()<GlobalVariables.limitePuntosListaEstados1_2_3) {
        		h.getListaEstados1().add(estadoBus);
        	}else {
        		if(h.getListaEstados2()==null) {
        			h.setListaEstados2(new ArrayList<EstadoBus>(Arrays.asList(estadoBus)));
        		}else {
        			if(h.getListaEstados2().size()<GlobalVariables.limitePuntosListaEstados1_2_3) {
            			h.getListaEstados2().add(estadoBus);
            		}else {
            			if(h.getListaEstados3()==null) {
                			h.setListaEstados3(new ArrayList<EstadoBus>(Arrays.asList(estadoBus)));
                		}else {
                			if(h.getListaEstados3().size()<GlobalVariables.limitePuntosListaEstados1_2_3) {
                				h.getListaEstados3().add(estadoBus);
                			}else {
                				throw new ResponseStatusException(
                					       HttpStatus.CONFLICT, "Se alcanzo el limite permitido de estados del bus en el Historial.");
                			}
                		}
            		}
        		}
        	}
        }else {
        	h = new HistorialEstadoBus();
        	h.setPlaca(placa);
        	h.setListaEstados1(new ArrayList<>(Arrays.asList(estadoBus)));
        	h.setLinea(linea);
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
	public void updateEstadoBusGET(String valor, int linea, String placa) throws JsonParseException, JsonMappingException, IOException {
		HistorialEstadoBus h;
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
        Gson mapper = new Gson();
		EstadoBus obj = mapper.fromJson(valor, EstadoBus.class);
        if(historialEstadoBusRepository.existsById(now.getTime()+"::"+placa)) {
        	h = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get();
        	if(h.getListaEstados1().size()<4000) {
        		h.getListaEstados1().add(obj);
        	}else {
        		if(h.getListaEstados2().size()<4000) {
        			h.getListaEstados2().add(obj);
        		}else {
        			h.getListaEstados3().add(obj);
        		}
        	}
        }else {
        	List<EstadoBus> eb = new ArrayList<>();
        	eb.add(obj);
        	h = new HistorialEstadoBus(placa,eb);
        	h.setLinea(linea);
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
		EstadoBus obj = new EstadoBus(Integer.parseInt(attrib[0]),
										Integer.parseInt(attrib[1]),
										new Point(Double.parseDouble(attrib[2]),
										Double.parseDouble(attrib[3])),
										Boolean.parseBoolean(attrib[4]));
		if(historialEstadoBusRepository.existsById(now.getTime()+"::"+placa)) {
        	h = historialEstadoBusRepository.findById(now.getTime()+"::"+placa).get();
        	if(h.getListaEstados1().size()<4000) {
        		h.getListaEstados1().add(obj);
        	}else {
        		if(h.getListaEstados2().size()<4000) {
        			h.getListaEstados2().add(obj);
        		}else {
        			h.getListaEstados3().add(obj);
        		}
        	}
        }else {
        	List<EstadoBus> eb = new ArrayList<>();
        	eb.add(obj);
        	h = new HistorialEstadoBus();
        	h.setPlaca(placa);
        	h.setLinea(Integer.parseInt(attrib[5]));
        	h.setListaEstados1(eb);
        }
		historialEstadoBusRepository.save(h);
	}
	//Obtener trafico de buses online
	public void getTraficBus() throws ParseException, InterruptedException {
		//String r = rutaRepository.findById("11").get().getRutaGeoJson();
		//return r;
	}
	public void startSimulator(int linea, String placa) throws ParseException, InterruptedException {
		s.startSimulador(linea,placa);
	}
	public Map<String, Double> getCalculateTimeToStop(String idParada, String linea) throws InterruptedException {
		TimeControlParada tcp = timeControlParadaRepository.findByLinea(linea);
		List<EstadoBusTemporal> listEstadoBus = getEstadoActualBusByLinea(linea);
		Map<String,Double> listTiempoBus = new HashMap<String, Double>();
		for(EstadoBusTemporal eb : listEstadoBus) {
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
			Long result = (now.getTime().getTime() - eb.getCreationDate().getTime())/1000;
			if(result>=0 && result<=10) {
				List<Parada> par = null;
				Double sumKm = 0.0;
				boolean banSerch =true;
				while(banSerch){
					sumKm = sumKm + 0.1;
					par = (List<Parada>) paradaService.getParadasCercanasRadio(eb.getPosicionActual(), sumKm, linea);
					if(!par.isEmpty() || !par.equals(null)) {
						List<BetweenParada> validosParaEvaluar = findBetweenParadaForEvaluate(par,tcp);
						if(validosParaEvaluar == null) {
							banSerch =true;
						}else {
							for(int j=0;j<validosParaEvaluar.size();j++) {
								//evaluar puntos con la primera parada
								Parada p1 = paradaService.getParadaById(validosParaEvaluar.get(j).getIdparada1());
								//estado bus anterior
								double distanciaP1BusAnterior = p1.distance(eb.getPosicionAnterior(), "M");
								//estado bus actual
								double distanciaP1BusActual = p1.distance(eb.getPosicionActual(), "M");
								//evaluar puntos con la segunda parada
								Parada p2 = paradaService.getParadaById(validosParaEvaluar.get(j).getIdparada2());
								//estado bus anterior
								double distanciaP2BusAnterior = p2.distance(eb.getPosicionAnterior(), "M");
								//estado bus actual
								double distanciaP2BusActual = p2.distance(eb.getPosicionActual(), "M");
								if(distanciaP1BusActual>distanciaP1BusAnterior && distanciaP2BusActual<distanciaP2BusAnterior) {
									//BetweenParadas Valido para sacar su tiempo...
									//utilizare la posicion actual del bus
									double distanciaP1P2 = p1.distance(p2.getCoordenada(), "M");
									double porcentajeP2BusActual = distanciaP2BusActual*100/distanciaP1P2;//% para calcular el tiempo restante
									double tiempotarda = validosParaEvaluar.get(j).getTiempoPromedio()*porcentajeP2BusActual/100;
									System.out.println("El tiempo que tardara el bus a esa parada es: " + tiempotarda);
									boolean ban = true;
									String idParadaSiguente = p2.getId();
									while(ban) {
										ban = false;
										for(BetweenParada bParada : tcp.getListTime()) {
											if(idParadaSiguente.equals(bParada.getIdparada1())) {
												tiempotarda = tiempotarda + bParada.getTiempoPromedio();
												idParadaSiguente = bParada.getIdparada2();
												ban = true;
												break;
											}
										}
										if(idParadaSiguente.equals(idParada)) {
											ban = false;
											listTiempoBus.put(eb.getPlaca(), tiempotarda);
											banSerch =false;
										}
									}
								}else if(distanciaP1BusActual==distanciaP1BusAnterior || distanciaP2BusActual==distanciaP2BusAnterior){
									System.out.println("No se puede identificar... Buses en la misma posicion...");
								}
							}
						}	
					}
				}
			}
		}
		return listTiempoBus;
	}
	//TODO Pasar aqui valores del bus y paradas para evaluar
	public List<BetweenParada> findBetweenParadaForEvaluate(List<Parada> par, TimeControlParada tcp) {
		List<BetweenParada> validosParaEvaluar = null;
		for(Parada pAux:par) {//listas de paradas cercanas al bus
			for(BetweenParada oneBP:tcp.getListTime()) {
				if(oneBP.getIdparada1().equals(pAux.getId()) || oneBP.getIdparada2().equals(pAux.getId())) {
					if(validosParaEvaluar==null) {
						validosParaEvaluar = new ArrayList<BetweenParada>();
						validosParaEvaluar.add(oneBP);
					}else {
						boolean encontro = false;
						for(int x =0;x<validosParaEvaluar.size();x++) {
							if(validosParaEvaluar.get(x).equals(oneBP)) {
								encontro = true;
								break;
							}
						}
						if(encontro == false)
							validosParaEvaluar.add(oneBP);
					}
				}
			}
		}
		return validosParaEvaluar;
	}
	public List<EstadoBusTemporal> getEstadoActualBusByLinea(String linea) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);   
        now.set(Calendar.HOUR_OF_DAY, 0);
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(now.getTime());
        //todayAsString = "2019-05-26";
        List<EstadoBusTemporal> list = historialEstadoBusRepository.findLastEstadoBusByLinea(todayAsString, linea);
		return list;
	}
}