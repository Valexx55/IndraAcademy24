package edu.indra.cursos.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.indra.comun.entity.Curso;
import edu.indra.cursos.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

	
	@Autowired
	CursoService cursoService;
	
	Logger logger = LoggerFactory.getLogger(CursoController.class);
	
	@GetMapping // GET http://localhost:8081/curso
	public ResponseEntity<?> listarAlumnos() {
		ResponseEntity<?> responseEntity = null;
		Iterable<Curso> listado_cursos = null;

			listado_cursos = this.cursoService.findAll();
			responseEntity = ResponseEntity.ok(listado_cursos);

		return responseEntity;

	}

	@GetMapping("/{id}") // GET http://localhost:8081/curso/5
	public ResponseEntity<?> listarCursoPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;

			o_curso = this.cursoService.findById(id);
			if (o_curso.isPresent()) {
				Curso curso_leido = o_curso.get();
				responseEntity = ResponseEntity.ok(curso_leido);
			} else {
				// no había un alumno con ese ID
				responseEntity = ResponseEntity.noContent().build();
			}

		return responseEntity;

	}
	
	
	@PostMapping // POST http://localhost:8081/curso
	public ResponseEntity<?> insertarCurso(@RequestBody Curso alumno) {
		ResponseEntity<?> responseEntity = null;
		Curso curso_creado = null;

				curso_creado = this.cursoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(curso_creado);
			
		return responseEntity;

	}
	
	@PutMapping("/{id}") // PUT http://localhost:8081/curso/5
	public ResponseEntity<?> modificarCurso(@RequestBody Curso curso, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;
			
				o_curso = this.cursoService.update(curso, id);
	
				if (o_curso.isPresent()) {
					Curso curso_modificado = o_curso.get();
					responseEntity = ResponseEntity.ok(curso_modificado);
				} else {
					// no había un alumno con ese ID
					responseEntity = ResponseEntity.notFound().build();// 404
				}
	
		return responseEntity;

	}
	
	@DeleteMapping("/{id}") // DELETE http://localhost:8081/curso/8
	public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;

			this.cursoService.deleteById(id);
			responseEntity = ResponseEntity.ok().build();

		return responseEntity;

	}
}
