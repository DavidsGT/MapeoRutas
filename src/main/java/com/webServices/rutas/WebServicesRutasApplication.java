package com.webServices.rutas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.webServices.rutas.model.FileStorageProperties;
/**
 * Metodo Principal
 * @author Davids Adrian Gonzalez Tigrero
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableScheduling

@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class WebServicesRutasApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServicesRutasApplication.class, args);
	}
}