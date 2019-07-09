package com.webServices.rutas.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GlobalVariables {
	public static final Double coeficiente = 57.304;//Kilometros mal representados tube que multiplicar a este coficiente para poder representar sin problema
	public static final double distanceMaxBusesToParada = 0.003;//Busca buses a una distancia max de 3 metros a la redonda de una parada
	public static final int horaFinalSimulador = 23;//El simulador acaba en este tiempo
	public static final int horaInicioSimulador = 8;
	public static final int secondSimulatorSave = 5;//cada segundo guarda un dato de avance del bus de manera aleatoria
	public static final String timeScheduled  = "15 31 12 * * ?";//Hora que se lanza para el calculo de tiempos entre parada
	public static final String fechaNightCalculation  = "2019-07-01";//Si esta vacio coge el tiempo actual a menos q en este campo se especifique con una fecha yyyy-MM-dd
	public static final int limitListEstados = 4000;
	public static String confirmPlaca(String placa) {
		return placa.replace("-","")
					.toUpperCase();
	}
	public static Date getFechaDMA() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
		return now.getTime();
	}
	public static Date getFecha() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
		return now.getTime();
	}
	public static Boolean validateSimulator() {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
		if(now.get(Calendar.HOUR_OF_DAY) < horaFinalSimulador && now.get(Calendar.HOUR_OF_DAY) >= horaInicioSimulador) {
			return true;
		}else {
			throw new ResponseStatusException(
				       HttpStatus.CONFLICT, "Simulador fuera de tiempo.");
		}
	}
}