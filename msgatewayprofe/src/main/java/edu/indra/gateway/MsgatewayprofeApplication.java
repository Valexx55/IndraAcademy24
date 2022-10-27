package edu.indra.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsgatewayprofeApplication {
	
	/*
	 *PASOS PARA CONFIGURAR GATEWAY
	 *
	 * 1) CREAMOS PROYECTO SPRING STARTER Y ADD DEPENDENCIAS DE EUREKA CLIENT Y GATEWAY
	 * 2) ANOTAMOS EL MAIN CON @EnableEurekaClient 
	 * 3) CONFIGURAMOS LAS PROPERTIES
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(MsgatewayprofeApplication.class, args);
	}

}
