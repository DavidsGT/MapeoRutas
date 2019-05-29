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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonParseException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonMappingException;
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
import com.webServices.rutas.repository.ParadaRepository;
import com.webServices.rutas.repository.RutaRepository;
import com.webServices.rutas.repository.TimeControlParadaRepository;
import com.webServices.rutas.util.Simulators;

@Service
public class BusService {
	@Autowired
	private ParadaRepository paradaRepository;
	@Autowired
	private Simulators s;
	@Autowired
	private TimeControlParadaRepository timeControlParadaRepository;
	@Autowired
	private HistorialEstadoBusRepository historialEstadoBusRepository;
	@Autowired
	private RutaRepository rutaRepository;
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
	public String getTraficBus() throws ParseException, InterruptedException {
		String r = rutaRepository.findById("11").get().getRutaGeoJson();
		return r;
	}

	public void startSimulator(int linea, String placa) throws ParseException, InterruptedException {
		s.startSimulador(linea,placa);
	}

	public long getCalculateTimeToStop(String idParada, String linea) {
		List<String> idsParadas = rutaRepository.findById(linea).get().getListasParadas();
		TimeControlParada tcp = timeControlParadaRepository.findByLinea(linea);
		
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todayAsString = df.format(now.getTime());
        //todayAsString = "2019-05-17";
		List<EstadoBusTemporal> listEstadoBus = historialEstadoBusRepository.findLastEstadoBusByLinea(todayAsString, linea);
		for(EstadoBusTemporal eb : listEstadoBus) {
			Long result = (now.getTime().getTime() - eb.getCreationDate().getTime())/1000;
			if(result>0 && result<10) {
				List<Parada> par = null;
				Double sumKm = 0.0;
				while(par == null){
					sumKm = sumKm + 0.1;
					Circle circle = new Circle(eb.getPosicionActual(),new Distance((GlobalVariables.coeficiente*sumKm), Metrics.KILOMETERS));
					par = (List<Parada>) paradaRepository.findByCoordenadaWithin(circle);
					if(!par.isEmpty() || par.equals(null)) {
						List<Parada> listOutput = par.stream()
					            .filter(e -> idsParadas.contains(e.getId()))
					            .collect(Collectors.toList());
						if(listOutput == null) {
							par = null;
						}else {
							List<BetweenParada> validosParaEvaluar = new ArrayList<BetweenParada>();
							for(Parada pAux:listOutput) {//listas de paradas cercanas al bus
								for(BetweenParada oneBP:tcp.getListTime()) {
									if(oneBP.getIdparada1().equals(pAux.getId()) || oneBP.getIdparada2().equals(pAux.getId())) {
										for(BetweenParada b : validosParaEvaluar) {
											if(!b.equals(oneBP)) {
												validosParaEvaluar.add(oneBP);
											}
										}
									}
								}
							}
							for(BetweenParada bpEvaluar : validosParaEvaluar ) {
								//evaluar puntos con la primera parada
								Parada p1 = paradaRepository.findById(bpEvaluar.getIdparada1()).get();
								//estado bus anterior
								double distanciaP1BusAnterior = p1.distance(eb.getPosicionAnterior(), "M");
								//estado bus actual
								double distanciaP1BusActual = p1.distance(eb.getPosicionActual(), "M");
								//evaluar puntos con la segunda parada
								Parada p2 = paradaRepository.findById(bpEvaluar.getIdparada2()).get();
								//estado bus anterior
								double distanciaP2BusAnterior = p2.distance(eb.getPosicionAnterior(), "M");
								//estado bus actual
								double distanciaP2BusActual = p2.distance(eb.getPosicionActual(), "M");
								if(distanciaP1BusActual>distanciaP1BusAnterior && distanciaP2BusActual<distanciaP2BusAnterior) {
									//BetweenParadas Valido para sacar su tiempo...
									//utilizare la posicion actual del bus
									double distanciaP1P2 = p1.distance(p2.getCoordenada(), "M");
									double porcentajeP2BusActual = distanciaP2BusActual*100/distanciaP1P2;//% para calcular el tiempo restante
									double tiempotarda = bpEvaluar.getTiempoPromedio()*porcentajeP2BusActual/100;
									System.out.println("El tiempo que tardara el bus a esa parada es: " + tiempotarda);
									boolean ban = true;
									String idParadaSiguente = p2.getId();
									while(ban) {
										ban = false;
										for(BetweenParada bParada : tcp.getListTime()) {
											if(idParadaSiguente==bParada.getIdparada1()) {
												tiempotarda = tiempotarda + bParada.getTiempoPromedio();
												idParadaSiguente = bParada.getIdparada2();
												ban = true;
												break;
											}
										}
										if(!ban) {
											System.out.println("No hay parada siguiente...");
										}else {
											if(idParadaSiguente == idParada) {
												ban = false;
												System.out.println("Termino operacion, tiempo a esta parada es: " + tiempotarda);
											}
										}
									}
								}else if(distanciaP1BusActual==distanciaP1BusAnterior || distanciaP2BusActual==distanciaP2BusAnterior){
									//si alguna distancia es igual a la otra.. de ser asi debe tomar el siguiente punto anterior
									//y volver a evaluar.
									System.out.println("No se puede identificar... Buses en la misma posicion...");
								}else {// de no ser el caso seguira el while infinito colocar null a par
									par = null;
								}
							}
						}
					}
				}
				
			}
		}
		return 0;
	}
	//TODO Pasar aqui valores del bus y paradas para evaluar
	public void calcularTiempoBus() {
		
	}
	public List<EstadoBusTemporal> getEstadoActualBusByLinea(String linea) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);   
        now.set(Calendar.HOUR_OF_DAY, 0);
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(now.getTime());
        todayAsString = "2019-05-26";
        List<EstadoBusTemporal> list = historialEstadoBusRepository.findLastEstadoBusByLinea(todayAsString, linea);
		return list;
	}
}