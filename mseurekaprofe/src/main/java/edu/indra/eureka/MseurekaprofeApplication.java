package edu.indra.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//activo las dependencias de Eureka
public class MseurekaprofeApplication {
	
	/**
	 * PASOS PARA CONFIGURAR EUREKA
	 * 
	 * 1) Creamos un proyecto Starter y add Eureka Server
	 * 2) Add JAXB Glassfish 
	 * 3) Anotamos el main con @EnableEurekaServer
	 * 4) Configurar properties
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(MseurekaprofeApplication.class, args);
	}

}
