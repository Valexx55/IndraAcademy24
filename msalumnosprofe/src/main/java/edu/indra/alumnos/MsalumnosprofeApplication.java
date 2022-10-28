package edu.indra.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient //activamos el cliente de eureka
@EntityScan("edu.indra.comun")//con esta anotación, le ayudo a encontrar la entidad Curso (que la hemos movido a otro paquete
@EnableFeignClients //activamos el FEIGN 
//@ComponentScan//obligatorio usar si los componentes (servicios, repo y controller,) están fuera del paquete raíz
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
