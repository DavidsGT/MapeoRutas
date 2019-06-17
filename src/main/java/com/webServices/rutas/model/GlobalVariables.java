package com.webServices.rutas.model;

public class GlobalVariables {
	public static final Double coeficiente = 57.304;//Kilometros mal representados tube que multiplicar a este coficiente para poder representar sin problema
	public static final double distanceMaxBusesToParada = 0.003;//Busca buses a una distancia max de 3 metros a la redonda de una parada
	public static final int horaFinalSimulador = 20;//El simulador acaba en este tiempo
	public static final int secondSimulatorSave = 5;//cada segundo guarda un dato de avance del bus de manera aleatoria
	public static final String timeScheduled  = "0 15 20 * * ?";//Hora que se lanza para el calculo de tiempos entre parada
	public static final String fechaNightCalculation  = "";//Si esta vacio coge el tiempo actual a menos q en este campo se especifique con una fecha yyyy-MM-dd
}