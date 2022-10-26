package edu.indra.alumnos.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.indra.alumnos.dto.FraseChuckNorris;
import edu.indra.alumnos.repository.entity.Alumno;
import edu.indra.alumnos.service.AlumnoService;

//@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.GET})
@RestController
@RequestMapping("/alumno")
public class AlumnoController {
	
	//REST Http
	//GET - LEER UN ALUMNO
	//GET - LEER TODOS LOS ALUMNOS
	//POST - GUARDAR 1 ALUMNO NUEVO
	//PUT - MODIFICAR 1 ALUMNO EXISENTE
	//DELETE - BORRAR EL ALUMNO
	
	Logger logger = LoggerFactory.getLogger(AlumnoController.class);
	
	@Autowired
	AlumnoService alumnoService;
	
	@GetMapping("/obtenerAlumnoTest") //GET http://localhost:8081/alumno/obtenerAlumnoTest
	public Alumno obtenerAlumnoPrueba ()
	{
		Alumno alumno_devuelto = null;
			
			//public Alumno(Long id, String nombre, String apellido, String email, int edad, Date creadoEn)
			alumno_devuelto = new Alumno (3l, "PACO", "LOPEZ", "paco@correo.es", 23, new Date());//OBJETO ENTIDAD ESTADO TRANSIENT (NO TIENE RELACIÓN CON bd)
			
		
		return alumno_devuelto;
		
	}
	
	
	@GetMapping //GET http://localhost:8081/alumno
	public ResponseEntity<?> listarAlumnos ()
	{
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;
		
				logger.debug("en listarAlumnos ()");
				listado_alumnos = this.alumnoService.findAll();
				responseEntity = ResponseEntity.ok(listado_alumnos);
				logger.debug("salida listarAlumnos () " + listado_alumnos);
		
		return responseEntity;
		
	}
	
	@GetMapping("/{id}") //GET http://localhost:8081/alumno/5
	public ResponseEntity<?> listarAlumnoPorId (@PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> o_alumno = null;
		
				o_alumno = this.alumnoService.findById(id);
				if (o_alumno.isPresent())
				{
					Alumno alumno_leido = o_alumno.get();
					responseEntity = ResponseEntity.ok(alumno_leido);
				} else {
					//no había un alumno con ese ID
					responseEntity = ResponseEntity.noContent().build();
				}
				
		
		return responseEntity;
		
	}
	
	private ResponseEntity<?> obtenerErrores (BindingResult bindingResult)
	{
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> lista_errores = null;
			
				lista_errores = bindingResult.getAllErrors();
				lista_errores.forEach(oerror -> logger.error(oerror.toString()));
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lista_errores);
		
		return responseEntity;
		
	}
	
	@PostMapping//POST http://localhost:8081/alumno
	public ResponseEntity<?> insetarAlumno (@Valid @RequestBody Alumno alumno, BindingResult bindingResult)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;
		
				logger.debug("en insetarAlumno()");
				if (bindingResult.hasErrors())
				{
					//el alumno viene con errores
					logger.debug("el alumno viene con errores");
					responseEntity = obtenerErrores(bindingResult);
					
				} else {
					logger.debug("el alumno viene sin errores");
					alumno_creado = this.alumnoService.save(alumno);
					responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
				}
				
				
		
		return responseEntity;
		
	}
	

	@PutMapping("/{id}") //PUT http://localhost:8081/alumno/5
	public ResponseEntity<?> modificarAlumno (@Valid @RequestBody Alumno alumno, BindingResult bindingResult, @PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> o_alumno = null;
		
		if (bindingResult.hasErrors())
		{
			//el alumno viene con errores
			responseEntity = obtenerErrores(bindingResult);
		} else {
			o_alumno = this.alumnoService.update(alumno, id);
			
			if (o_alumno.isPresent())
			{
				Alumno alumno_modificado = o_alumno.get();
				responseEntity = ResponseEntity.ok(alumno_modificado);
			} else {
				//no había un alumno con ese ID
				responseEntity = ResponseEntity.notFound().build();//404
			}
		}
		
				
				
		
		return responseEntity;
		
	}
	

	@DeleteMapping("/{id}") //DELETE http://localhost:8081/alumno/8
	public ResponseEntity<?> eliminarAlumno (@PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		
				//var saludo = "HOLA";
				//saludo.charAt(5);// https://developer.oracle.com/learn/technical-articles/jdk-10-local-variable-type-inference
				
				this.alumnoService.deleteById(id);
				responseEntity = ResponseEntity.ok().build();
				
		return responseEntity;
		
	}
	
	
	@GetMapping("/obtenerFraseAleatoriaChuckNorris") //GET http://localhost:8081/alumno/obtenerFraseAleatoriaChuckNorris
	public ResponseEntity<?> obtenerFraseAleatoriaChuckNorris ()
	{
		ResponseEntity<?> responseEntity = null;
		Optional<FraseChuckNorris> o_frase = null;
		
				logger.debug("en obtenerFraseAleatoriaChuckNorris()");
				o_frase = this.alumnoService.obtenerFraseAleatoriaChuckNorris();
				if (o_frase.isPresent())
				{
					
					FraseChuckNorris frase = o_frase.get();
					responseEntity = ResponseEntity.ok(frase);
					logger.debug("FRASE recuperada " + frase);
				} else {
					//no ha obtenido una frase
					logger.debug("no ha obtenido una frase");
					responseEntity = ResponseEntity.noContent().build();
				}
				
		
		return responseEntity;
		
	}
	
	
	

}