package com.webServices.rutas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.webServices.rutas.model.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class WebServicesRutasApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServicesRutasApplication.class, args);
	}
}