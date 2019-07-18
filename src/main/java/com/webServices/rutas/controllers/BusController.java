package com.webServices.rutas.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonParseException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonMappingException;
import com.webServices.rutas.model.Bus;
import com.webServices.rutas.model.EstadoBus;
import com.webServices.rutas.model.EstadoBusTemporal;
import com.webServices.rutas.model.TimeControlParada;
import com.webServices.rutas.services.BusService;

/**
 * Contiene los requestMapping de Buses y los asocia a sus respectivos servicios en {@link BusService}.
 * @author Davids Adrian Gonzalez Tigrero
 * @version 1.0
 * @see BusService
 */
@RestController
@RequestMapping("buses")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BusController {
	
	/**
	 * Instancia de los servicios para Bus
	 * @see {@link BusService}
	 */
	@Autowired
	private BusService busService;
	
	/**
	 * Metodo que Mapea "/buses", RequestMethod es GET, se enlaza al servicio {@link BusService#getAllBus()}
	 * Retorna datos de todos los {@link Bus} registrados
	 * @return Lista de {@link Bus}
	 * @see {@link BusService#getAllBus()}
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public List<Bus> getAllBuses(){
		return busService.getAllBus();
	}

	/**
	 * Metodo que Mapea "/buses/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link BusService#getAllBusEstadoTrue()} 
	 * y retorna todos los buses incluye eliminados logicamente.
	 * @return Buses incluye eliminados logicamente
	 * @see {@link BusService#getAllBusEstadoTrue()}
	 */
	@GetMapping("/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public List<Bus> getAllBusesIgnoreEstado(){
		return busService.getAllBusIgnoreEstado();
	}
	
	/**
	 * Metodo que Mapea "/buses/{placa}", RequestMethod es GET, se enlaza al servicio {@link BusService#getBus(String)} 
	 * y retorna Bus
	 * @param placa - Numero de PLACA del bus que desea los datos
	 * @return Datos del Bus
	 * @see {@link BusService#getBus(String)}
	 */
	@GetMapping("/{placa}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_MOVIL') or hasRole('USER_WEB')")
	public Bus getBus(@PathVariable String placa) {
		return busService.getBus(placa);
	}
	
	/**
	 * Metodo que Mapea "/buses/{placa}/ignoreEstado", RequestMethod es GET, se enlaza al servicio {@link BusService#getBus(String)} 
	 * y retorna el historial del estado del bus
	 * @param placa - Numero de PLACA del bus que desea los datos
	 * @return Datos del Bus
	 * @see {@link BusService#getBus(String)}
	 */
	@GetMapping("/{placa}/ignoreEstado")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public Bus getBusIgnoreEstado(@PathVariable String placa) {
		return busService.getBusIgnoreEstado(placa);
	}
	
	/**
	 * Metodo que Mapea "/buses/byCooperativa/{idCooperativa}", RequestMethod es GET, se enlaza al servicio {@link BusService#getBusesByIdCooperativa(String)} y 
	 * retorna datos de buses de una cooperativa
	 * @param idCooperativa - ID de la cooperativa a la pertenecen N Buses
	 * @return Lista de Buses de una Cooperativa
	 * @see {@link BusService#getBusesByIdCooperativa(String)}
	 */
	@GetMapping("/byCooperativa/{idCooperativa}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Iterable<Bus> getAllBusesByCooperativa(@PathVariable String idCooperativa){
		return busService.getBusesByIdCooperativa(idCooperativa);
	}
	
	/**
	 * Metodo que Mapea "/buses/{placa}/historialSeguimientoBus/{fecha}", RequestMethod es GET, se enlaza al servicio {@link BusService#getHistorialEstadoBusAllByPlaca(String)} 
	 * y retorna el historial del estado del bus segun la fecha que se desee
	 * @param placa - Placa del Bus del que se desea el historial
	 * @return Historial del bus
	 * @see {@link BusService#getHistorialEstadoBusAllByPlaca(String)}
	 */
	@GetMapping("/{placa}/historialEstado/{fecha}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public List<EstadoBus> getHistorialEstadoBusByPlaca(@PathVariable String placa,@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date fecha){
		return busService.getHistorialEstadoBusAllByPlacaByFecha(placa,fecha);
	}

	/**
	 * Metodo  que Mapea "/buses/{placa}/estadoActual", RequestMethod es GET, se enlaza al servicio {@link BusService#getEstadoActualBus(String)}
	 * y retorna Estado actual de un bus segun su placa
	 * @param placa - Placa del Bus a obtener estado actual
	 * @return Datos del Estado Actual del Bus
	 * @see {@link BusService#getEstadoActualBus(String)}
	 */
	@GetMapping("/{placa}/estadoActual")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public EstadoBusTemporal getEstadoActualBus(@PathVariable String placa) {
		return busService.getEstadoActualBus(placa);
	}
	
	/**
	 * Metodo  que Mapea "/buses/{linea}/allEstadoActual", RequestMethod es GET, se enlaza al servicio {@link BusService#getEstadoActualBusByLinea(String)}
	 * y retorna Estado actual de un bus segun su placa
	 * @param linea - linea de la Cooperativa a obtener los Estados actuales
	 * @return Lista de Estados de los Buses segun una linea de Cooperativa
	 * @see {@link BusService#getEstadoActualBusByLinea(String)}
	 */
	@GetMapping("/{linea}/allEstadoActual")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public List<EstadoBusTemporal> getEstadoActualBusByLinea(@PathVariable String linea) {
		return busService.getEstadoActualBusByLinea(linea);
	}
	
	/**
	 * Metodo  que Mapea "/buses/allEstadoActual", RequestMethod es GET, se enlaza al servicio {@link BusService#getAllEstadoActualBus()}
	 * y retornalos Estado actual de todos los buses
	 * @return Lista de Estados de los Buses
	 * @see {@link BusService#getEstadoActualBusByLinea(String)}
	 */
	@GetMapping("/allEstadoActual")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public List<EstadoBusTemporal> getAllEstadoActualBus() {
		return busService.getAllEstadoActualBus();
	}

	/**
	 * Metodo que Mapea "/buses", RequestMethod es POST, se enlaza al servicio {@link BusService#addBus(Bus)} 
	 * y retorna Datos del Bus registrado
	 * @param bus - Datos del Bus a Registrar
	 * @return Bus Registrado
	 * @see {@link BusService#addBus(Bus)}
	 */
	@PostMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Bus addBus(@RequestBody Bus bus) {
		return busService.addBus(bus);
	}

	/**
	 * Metodo que Mapea "/buses/{placa}/estado", RequestMethod es PUT, se enlaza al servicio {@link BusService#updateEstadoBus(EstadoBus, String)}
	 * @param estadoBus - Estado del Bus a Actualizar (Velocidad, Num. Pasajero, Ruta Actual)
	 * @param placa - Placa del Bus a actualizar su estado Actual
	 * @see {@link BusService#updateEstadoBus(EstadoBus, String)}
	 */
	@PutMapping("/{placa}/estado/{linea}")
	@ResponseStatus(value=HttpStatus.OK, reason="Estado de Bus Guardado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('BUS_DEVICE')")
	public void updateEstadoBus(@RequestBody EstadoBus estadoBus,@PathVariable String placa, @PathVariable int linea) {
		busService.updateEstadoBus(estadoBus,placa,linea);
	}
	
	/**
	 * Metodo  que Mapea "/buses/{placa}/estado/{valor}", RequestMethod es GET, se enlaza al servicio {@link BusService#updateEstadoBusGET(String, String)}.
	 * Metodo no recomendable.
	 * Registrar el Estado del Bus.
	 * @deprecated Metodo que sirve de apoyo al Dispositivo Bus para facilitar su envio por ahora, pero el metodo que realmente se deberian usar es: {@link BusController#updateEstadoBus(EstadoBus, String)}
	 * @param valor - Cadena Json de los Datos actuales del Estado Bus
	 * @param placa - Placa del Bus que se desea setear el estado del bus.
	 * @throws JsonParseException - Errores en el 
	 * @throws JsonMappingException
	 * @throws IOException
	 * @see {@link BusService#updateEstadoBusGET(String, String) , {@link BusController#getEstadoActualBus(String)}}
	 */
	@GetMapping("/{placa}/estado/{linea}/{valor}")
	@ResponseStatus(value=HttpStatus.OK, reason="Estado de Bus Guardado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('BUS_DEVICE')")
	public void updateEstadoBusGET(@PathVariable String valor,@PathVariable int linea,@PathVariable String placa) throws JsonParseException, JsonMappingException, IOException {
		busService.updateEstadoBusGET(valor,linea,placa);
	}
	
	/**
	 * Metodo  que Mapea "/buses/{placa}/estadoBusAlternative/{valor}", RequestMethod es GET, se enlaza al servicio {@link BusService#updateEstadoBusGET(String, String)}.
	 * Metodo no recomendable.
	 * Registrar Estado del Bus
	 * @deprecated Metodo que sirve de apoyo al Dispositivo Bus para facilitar su envio por ahora, pero el metodo que realmente se deberian usar es: {@link BusController#updateEstadoBus(EstadoBus, String)}
	 * @param valor - Datos actuales del Bus.
	 * @param placa - Placa del Bus que se desea setear el estado del bus.
	 * @throws JsonParseException - Solo si la cadena String no cumple el Formato Json
	 * @throws JsonMappingException  - Solo si la cadena String no cumple el Formato Json
	 * @throws IOException
	 * @see {@link BusService#updateEstadoBusGETAlternative(String, String) , {@link BusController#getEstadoActualBus(String)}}
	 */
	@GetMapping("/{placa}/estadoBusAlternative/{valor}")
	@ResponseStatus(value=HttpStatus.OK, reason="Estado de Bus Guardado con exito.")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('BUS_DEVICE')")
	public void updateEstadoBusGETAlternative(@PathVariable String valor,@PathVariable String placa) throws JsonParseException, JsonMappingException, IOException {
		busService.updateEstadoBusGETAlternative(valor,placa);
	}

	/**
	 * Metodo que Mapea "/buses", RequestMethod es PUT, se enlaza al servicio {@link BusService#updateBus(Bus)}.
	 * Actualizar Bus.
	 * @param bus - Datos del Bus al que se actualizaran los datos
	 * @return Bus Actualizado
	 * @see {@link BusService#updateBus(Bus)}
	 */
	@PutMapping
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public Bus updateBus(@RequestBody Bus bus) {
		return busService.updateBus(bus);
	}
	
	/**
	 * Metodo que Mapea "/buses/{placa}", RequestMethod es DELETE, se enlaza al servicio {@link BusService#deleteBus(String)}.
	 * Eliminar un Bus.
	 * @param placa - PLACA del bus al que se desea eliminar
	 * @see {@link BusService#deleteBus(String)}
	 */
	@DeleteMapping("/{placa}")
	@ResponseStatus(value=HttpStatus.OK, reason="Bus eliminado correctamente.")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB')")
	public void deleteBus(@PathVariable String placa) {
		busService.deleteBus(placa);
	}
	
	/**
	 * Metodo que Mapea "/buses/{placa}/physical", RequestMethod es DELETE, se enlaza al servicio {@link BusService#deleteBusPhysical(String)}.
	 * Eliminar de la base de datos un Bus .
	 * @param placa - PLACA del bus al que se desea eliminar
	 * @see {@link BusService#deleteBusPhysical(String)}
	 */
	@DeleteMapping("/{placa}/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Bus eliminado de la Base de Datos correctamente.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteBusPhysical(@PathVariable String placa) {
		busService.deleteBusPhysical(placa);
	}
	
	/**
	 * Metodo que Mapea "/buses/physical", RequestMethod es DELETE, se enlaza al servicio {@link BusService#deleteAllBusPhysical()}.
	 * Eliminar de la base de datos todos los Buses Registrados.
	 * @see {@link BusService#deleteAllBusPhysical()}
	 */
	@DeleteMapping("/physical")
	@ResponseStatus(value=HttpStatus.OK, reason="Buses eliminados de la Base de Datos correctamente.")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void deleteAllBusPhysical() {
		busService.deleteAllBusPhysical();
	}
	
	/**
	 * Metodo que Mapea "/buses/simulado/{linea}/{placa}", RequestMethod es POST, se enlaza al servicio {@link BusService#startSimulator(int, String)}.
	 * Inicial el simulador de un recorrido de un Bus.
	 * @param linea - linea de cooperativa a la pertenece el Bus.
	 * @param placa - placa del bus que va a realizar el recorrido.
	 * @see {@link BusService#startSimulator(int, String)}
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	@PostMapping("/simulador/{linea}/{placa}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public void startSimulatorBus(@PathVariable int linea,@PathVariable String placa) throws ParseException, InterruptedException {
		busService.startSimulator(linea, placa);
	}
	
	/**
	 * Metodo que Mapea "/buses/calculateTimetoStop/{idParada}/{linea}", RequestMethod es GET, se enlaza al servicio {@link BusService#getCalculateTimeToStop(String, String)}.
	 * Calcula el tiempo que taradara los buses pertenecientes a una Linea de Cooperativa en llegar a una parada seleccionada.
	 * @param idParada - parada perteneciente a la linea de cooperativa
	 * @param linea - Linea de cooperativa para realizar el calculo con sus respectivos buses.
	 * @return lista de placas de buses con sus respectivos tiempos de llegada a una cooperativa.
	 * @throws InterruptedException
	 */
	@GetMapping("/calculateTimeToStop/{idParada}/{linea}")
	@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER_WEB') or hasRole('USER_MOVIL')")
	public Map<String, Double> getCalculateTimeToStop(@PathVariable String idParada,@PathVariable String linea) throws InterruptedException{
		return busService.getCalculateTimeToStop(idParada,linea);
	}
	
	/**
	 * Metodo que Mapea "/buses/traficBus", RequestMethod es GET, se enlaza al servicio {@link BusService#getTraficBus()}.
	 * Verifica zonas de alto trafico de buses dentro de una zona urbana.
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	@GetMapping("/traficBus")
	@PreAuthorize("hasRole('ADMINISTRATOR')  or hasRole('USER_WEB')")
	public void getTraficBus() throws ParseException, InterruptedException{
		busService.getTraficBus();
	}
	@PostMapping("/timeControlParada/{linea}")
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public TimeControlParada addTimeControl(@PathVariable String linea){
		return busService.crearTimeControlParada(linea);
	}
}