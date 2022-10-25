package edu.indra.alumnos.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.indra.alumnos.repository.entity.Alumno;
import edu.indra.alumnos.service.AlumnoService;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
	
	//REST Http
	//GET - LEER UN ALUMNO
	//GET - LEER TODOS LOS ALUMNOS
	//POST - GUARDAR 1 ALUMNO NUEVO
	//PUT - MODIFICAR 1 ALUMNO EXISENTE
	//DELETE - BORRAR EL ALUMNO
	
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
		
				listado_alumnos = this.alumnoService.findAll();
				responseEntity = ResponseEntity.ok(listado_alumnos);
		
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
	
	@PostMapping//POST http://localhost:8081/alumno
	public ResponseEntity<?> insetarAlumno (@RequestBody Alumno alumno)
	{
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;
		
				alumno_creado = this.alumnoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
				
		
		return responseEntity;
		
	}
	

	@PutMapping("/{id}") //PUT http://localhost:8081/alumno/5
	public ResponseEntity<?> modificarAlumno (@RequestBody Alumno alumno, @PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> o_alumno = null;
		
				o_alumno = this.alumnoService.update(alumno, id);
				
				if (o_alumno.isPresent())
				{
					Alumno alumno_modificado = o_alumno.get();
					responseEntity = ResponseEntity.ok(alumno_modificado);
				} else {
					//no había un alumno con ese ID
					responseEntity = ResponseEntity.notFound().build();//404
				}
				
		
		return responseEntity;
		
	}
	

	@DeleteMapping("/{id}") //DELETE http://localhost:8081/alumno/8
	public ResponseEntity<?> eliminarAlumno (@PathVariable Long id)
	{
		ResponseEntity<?> responseEntity = null;
		
				var saludo = "HOLA";
				saludo.charAt(5);
				
				this.alumnoService.deleteById(id);
				responseEntity = ResponseEntity.ok().build();
				
		return responseEntity;
		
	}

}