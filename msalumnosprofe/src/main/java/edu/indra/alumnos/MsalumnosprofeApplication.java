package edu.indra.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //activamos el cliente de eureka
public class MsalumnosprofeApplication {
	
	/**
	 * PARA CONFIGUAR EL CLIENTE EUREKA
	 * 
	 * 1) ADD DEPENDECIA DE EUREKA-CLIENT
	 * 2) ADD LA ANOTACIÓN @EnableEurekaClient EN EL MAIN DEL MS
	 * 3) ADD CONFIG PROPERTIES
	 * 
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosprofeApplication.class, args);
	}

}
