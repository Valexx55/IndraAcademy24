package edu.indra.alumnos.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//en esta clase, vamos a GESTIONAR todas las excepciones del microservicio

@RestControllerAdvice(basePackages = {"edu.indra.alumnos"})
public class GestionExcepciones {
	
	//para cada tipo excepción que yo quiera gestionar, necesito un método al que asociarla
	
	 @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)//cuando ocurra este tipo de fallo, llamas a este método
	 public ResponseEntity<?> errorBorradoNoExiste (EmptyResultDataAccessException e)
	 {
		 ResponseEntity<?> responseEntity = null;
		 String mensaje_error = null;
		 	
		 	mensaje_error = "ERROR AL BORRAR ALUMNO "+ e.getMessage();
		 	responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje_error);
		 
		 return responseEntity;
	 }
	 
	 @ExceptionHandler(Throwable.class)//cuando ocurra este tipo de fallo, llamas a este método
	 public ResponseEntity<?> errorGenerico (Throwable e)
	 {
		 ResponseEntity<?> responseEntity = null;
		 String mensaje_error = null;
		 	
		 	mensaje_error = "ERROR GENÉRICO "+ e.getMessage();
		 	responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje_error);
		 
		 return responseEntity;
	 }

}
