package edu.indra.alumnos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.indra.alumnos.repository.entity.Alumno;
import edu.indra.alumnos.service.AlumnoService;

@RestController
public class AlumnoController {
	
	//REST Http
	//GET - LEER UN ALUMNO
	//GET - LEER TODOS LOS ALUMNOS
	//POST - GUARDAR 1 ALUMNO NUEVO
	//PUT - MODIFICAR 1 ALUMNO EXISENTE
	//DELETE - BORRAR EL ALUMNO
	
	@Autowired
	AlumnoService alumnoService;
	
	@GetMapping("/obtenerAlumnoTest") //GET http://localhost:8081/obtenerAlumnoTest
	public Alumno obtenerAlumnoPrueba ()
	{
		Alumno alumno_devuelto = null;
			
			//public Alumno(Long id, String nombre, String apellido, String email, int edad, Date creadoEn)
			alumno_devuelto = new Alumno (3l, "PACO", "LOPEZ", "paco@correo.es", 23, new Date());//OBJETO ENTIDAD ESTADO TRANSIENT (NO TIENE RELACIÓN CON bd)
			
		
		return alumno_devuelto;
		
	}
	
	
	@GetMapping //GET http://localhost:8081/
	public ResponseEntity<?> listarAlumnos ()
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;
		
				listado_alumnos = this.alumnoService.findAll();
				responseEntity = ResponseEntity.ok(listado_alumnos);
		
		return responseEntity;
		
	}

}
