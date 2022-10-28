package edu.indra.alumnos.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.indra.alumnos.dto.FraseChuckNorris;
import edu.indra.alumnos.service.AlumnoService;
import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;

//@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.GET})
@RestController
@RequestMapping("/alumno")
public class AlumnoController {

	// REST Http
	// GET - LEER UN ALUMNO
	// GET - LEER TODOS LOS ALUMNOS
	// POST - GUARDAR 1 ALUMNO NUEVO
	// PUT - MODIFICAR 1 ALUMNO EXISENTE
	// DELETE - BORRAR EL ALUMNO
	
	@Value("${instancia}")
	String nombre_instancia;
	
	@Autowired
	Environment environment;

	Logger logger = LoggerFactory.getLogger(AlumnoController.class);

	@Autowired
	AlumnoService alumnoService;

	@GetMapping("/obtenerAlumnoTest") // GET http://localhost:8081/alumno/obtenerAlumnoTest
	public Alumno obtenerAlumnoPrueba() {
		Alumno alumno_devuelto = null;

		// public Alumno(Long id, String nombre, String apellido, String email, int
		// edad, Date creadoEn)
			alumno_devuelto = new Alumno(3l, "PACO", "LOPEZ", "paco@correo.es", 23, new Date());// OBJETO ENTIDAD ESTADO
																							// TRANSIENT (NO TIENE
																							// RELACIÓN CON bd)

		return alumno_devuelto;

	}

	@GetMapping // GET http://localhost:8081/alumno
	public ResponseEntity<?> listarAlumnos() {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en listarAlumnos ()");
			logger.debug("ATENTIDIDO POR INSTANCIA " + nombre_instancia);
			logger.debug("ATENTIDIDO EN EL PUERTO " + environment.getProperty("local.server.port"));
			listado_alumnos = this.alumnoService.findAll();
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida listarAlumnos () " + listado_alumnos);

		return responseEntity;

	}

	@GetMapping("/{id}") // GET http://localhost:8081/alumno/5
	public ResponseEntity<?> listarAlumnoPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> o_alumno = null;

			o_alumno = this.alumnoService.findById(id);
			if (o_alumno.isPresent()) {
				Alumno alumno_leido = o_alumno.get();
				responseEntity = ResponseEntity.ok(alumno_leido);
			} else {
				// no había un alumno con ese ID
				responseEntity = ResponseEntity.noContent().build();
			}

		return responseEntity;

	}

	private ResponseEntity<?> obtenerErrores(BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> lista_errores = null;

			lista_errores = bindingResult.getAllErrors();
			lista_errores.forEach(oerror -> logger.error(oerror.toString()));
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lista_errores);

		return responseEntity;

	}

	@PostMapping // POST http://localhost:8081/alumno
	public ResponseEntity<?> insetarAlumno(@Valid @RequestBody Alumno alumno, BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;

			logger.debug("en insetarAlumno()");
			if (bindingResult.hasErrors()) {
				// el alumno viene con errores
				logger.debug("el alumno viene con errores");
				responseEntity = obtenerErrores(bindingResult);
	
			} else {
				logger.debug("el alumno viene sin errores");
				alumno_creado = this.alumnoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
			}

		return responseEntity;

	}
	
	
	
	@PostMapping ("/crear-con-foto") // POST http://localhost:8081/alumno/crear-con-foto
	public ResponseEntity<?> insetarAlumnoConFoto(@Valid Alumno alumno, BindingResult bindingResult, MultipartFile archivo ) throws IOException {
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_creado = null;

			logger.debug("en insetarAlumno()");
			if (bindingResult.hasErrors()) {
				// el alumno viene con errores
				logger.debug("el alumno viene con errores");
				responseEntity = obtenerErrores(bindingResult);
	
			} else {
				logger.debug("el alumno viene sin errores");
				
				if (!archivo.isEmpty())
				{
					logger.debug("el alumno viene con foto");
					try {
						alumno.setFoto(archivo.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error("Error al tratar la foto", e);
						throw e;
					}
				}
				
				alumno_creado = this.alumnoService.save(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
			}

		return responseEntity;

	}
	
	
	@PutMapping("/editar-con-foto/{id}") //PUT localhost:8081/alumno/id
	public ResponseEntity<?> modificarAlumnoConFoto (@Valid Alumno alumno, BindingResult bindingResult, MultipartFile archivo, @PathVariable Long id) throws IOException
	{
		 ResponseEntity<?> responseEntity = null;
		 Optional<Alumno> optional_alumno = null;
		 
		 	this.logger.debug("modificarAlumno()");
		 	if (bindingResult.hasErrors())
		 	{
		 		//viene con errores
		 		//devolver un mensaje de error //BAD REQUEST -le enviamos los fallos
		 	
		 		responseEntity = obtenerErrores(bindingResult);
		 	} else {
		 		//sin fallos- seguimos con el update
		 		this.logger.debug("modificarAlumno() - TRAE ERRORES");
		 		
		 		
		 		if (!archivo.isEmpty())
		 		{
		 			try {
						alumno.setFoto(archivo.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error("Error al actualizar el alumno con foto", e);
						throw e;
					}
		 		}
		 		
		 		optional_alumno = this.alumnoService.update(alumno, id);
			 	if (optional_alumno.isPresent())
			 	{
			 		responseEntity = ResponseEntity.ok(optional_alumno.get());
			 	} else 
			 	{
			 		//no existía
			 		responseEntity = ResponseEntity.notFound().build();
			 	}
		 	}
		 
		 
		 return responseEntity; //este será el HTTP de vuelta
	}
	
	
	@GetMapping("/obtenerFoto/{id}") // GET http://localhost:8081/alumno/obtenerFoto/5
	public ResponseEntity<?> obtenerFotoAlumno(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> o_alumno = null;
		Resource imagen = null;

			o_alumno = this.alumnoService.findById(id);
			if (o_alumno.isPresent()&&o_alumno.get().getFoto()!=null) {
				Alumno alumno_leido = o_alumno.get();
				imagen = new ByteArrayResource (alumno_leido.getFoto());
				responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
			} else {
				// no había un alumno con ese ID
				responseEntity = ResponseEntity.noContent().build();
			}

		return responseEntity;

	}


	@PutMapping("/{id}") // PUT http://localhost:8081/alumno/5
	public ResponseEntity<?> modificarAlumno(@Valid @RequestBody Alumno alumno, BindingResult bindingResult,
			@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> o_alumno = null;

			if (bindingResult.hasErrors()) {
				// el alumno viene con errores
				responseEntity = obtenerErrores(bindingResult);
			} else {
				o_alumno = this.alumnoService.update(alumno, id);
	
				if (o_alumno.isPresent()) {
					Alumno alumno_modificado = o_alumno.get();
					responseEntity = ResponseEntity.ok(alumno_modificado);
				} else {
					// no había un alumno con ese ID
					responseEntity = ResponseEntity.notFound().build();// 404
				}
			}

		return responseEntity;

	}

	@DeleteMapping("/{id}") // DELETE http://localhost:8081/alumno/8
	public ResponseEntity<?> eliminarAlumno(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;

		// var saludo = "HOLA";
		// saludo.charAt(5);//
		// https://developer.oracle.com/learn/technical-articles/jdk-10-local-variable-type-inference

			this.alumnoService.deleteById(id);
			responseEntity = ResponseEntity.ok().build();

		return responseEntity;

	}

	@GetMapping("/obtenerFraseAleatoriaChuckNorris") // GET http://localhost:8081/alumno/obtenerFraseAleatoriaChuckNorris
	public ResponseEntity<?> obtenerFraseAleatoriaChuckNorris() {
		ResponseEntity<?> responseEntity = null;
		Optional<FraseChuckNorris> o_frase = null;

			logger.debug("en obtenerFraseAleatoriaChuckNorris()");
			o_frase = this.alumnoService.obtenerFraseAleatoriaChuckNorris();
			if (o_frase.isPresent()) {
	
				FraseChuckNorris frase = o_frase.get();
				responseEntity = ResponseEntity.ok(frase);
				logger.debug("FRASE recuperada " + frase);
			} else {
				// no ha obtenido una frase
				logger.debug("no ha obtenido una frase");
				responseEntity = ResponseEntity.noContent().build();
			}

		return responseEntity;

	}

	@GetMapping("/obtenerAlumnosEnRangoEdad") // GET http://localhost:8081/alumno/obtenerAlumnosEnRangoEdad?edadmin=5&edamax=8
	public ResponseEntity<?> obtenerAlumnosEnRangoEdad(@RequestParam(name = "edadmin") int edadmin,
			@RequestParam(name = "edamax") int edamax) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en obtenerAlumnosEnRangoEdad ()");
			listado_alumnos = this.alumnoService.findByEdadBetween(edadmin, edamax);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida obtenerAlumnosEnRangoEdad () " + listado_alumnos);

		return responseEntity;

	}
	
	@GetMapping("/obtenerAlumnosEnRangoEdadPaginado") // GET http://localhost:8081/alumno/obtenerAlumnosEnRangoEdadPaginado?edadmin=5&edamax=8&page=0&size=3
	public ResponseEntity<?> obtenerAlumnosEnRangoEdadPaginado(@RequestParam(name = "edadmin") int edadmin,
			@RequestParam(name = "edamax") int edamax, Pageable pageable) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en obtenerAlumnosEnRangoEdadPaginado ()");
			listado_alumnos = this.alumnoService.findByEdadBetween(edadmin, edamax, pageable);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida obtenerAlumnosEnRangoEdadPaginado () " + listado_alumnos);

		return responseEntity;

	}

	@GetMapping("/obtenerAlumnosPorNombreLike") // GET
												// http://localhost:8081/alumno/obtenerAlumnosPorNombreLike?buscar=mar
	public ResponseEntity<?> obtenerAlumnosPorNombreLike(@RequestParam(name = "buscar") String buscar) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en obtenerAlumnosPorNombre ()");
			listado_alumnos = this.alumnoService.findByNombreLike(buscar);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida obtenerAlumnosPorNombre ()");

		return responseEntity;
	}

	@GetMapping("/obtenerAlumnosNombreContiene") // GET http://localhost:8081/alumno/obtenerAlumnosNombreContiene?cadena=PAC
	public ResponseEntity<?> obtenerAlumnosNombreContiene(@RequestParam(name = "cadena") String cadena) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

		logger.debug("en obtenerAlumnosNombreContiene ()");
		listado_alumnos = this.alumnoService.findByNombreContaining(cadena);
		responseEntity = ResponseEntity.ok(listado_alumnos);
		logger.debug("salida obtenerAlumnosEnRangoEdad () " + listado_alumnos);

		return responseEntity;

	}

	@GetMapping("/listadoAlumnosConNombreOApellidoJPQL/{patron}") // GET http://localhost:8081/alumno/listadoAlumnosConNombreOApellidoJPQL/p
	public ResponseEntity<?> listadoAlumnosPorNombreOApellidoJPQL(@PathVariable String patron) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en listadoAlumnosConNombreOApellidoJPQL ()");
			listado_alumnos = this.alumnoService.busquedaPorNombreOApellidoJPQL(patron);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida listadoAlumnosConNombreOApellidoJPQL () " + listado_alumnos);

		return responseEntity;

	}
	
	@GetMapping("/listadoAlumnosConNombreOApellidoNativa/{patron}") // GET http://localhost:8081/alumno/listadoAlumnosConNombreOApellidoNativa/p
	public ResponseEntity<?> listadoAlumnosPorNombreOApellidoNativa(@PathVariable String patron) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en listadoAlumnosPorNombreOApellidoNativa ()");
			listado_alumnos = this.alumnoService.busquedaPorNombreOApellidoNativa(patron);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida listadoAlumnosPorNombreOApellidoNativa () " + listado_alumnos);

		return responseEntity;

	}
	
	@GetMapping("/obtenerAlumnosRegistradosHoy") // GET http://localhost:8081/alumno/obtenerAlumnosRegistradosHoy
	public ResponseEntity<?> obtenerAlumnosRegistradosHoy() {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en obtenerAlumnosRegistradosHoy ()");
			listado_alumnos = this.alumnoService.procedimientoAlumnosAltaHoy();
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida obtenerAlumnosRegistradosHoy () " + listado_alumnos);

		return responseEntity;

	}
	
	@GetMapping("/obtenerEstadisticosEdadAlumnos") // GET http://localhost:8081/alumno/obtenerEstadisticosEdadAlumnos
	public ResponseEntity<?> obtenerEstadisticosEdadAlumnos() {
		ResponseEntity<?> responseEntity = null;
		Map<String, Number> mapa_edades = null;

			logger.debug("en obtenerEstadisticosEdadAlumnos ()");
			mapa_edades = this.alumnoService.procedimientoEstadisticosEdad();
			responseEntity = ResponseEntity.ok(mapa_edades);
			logger.debug("salida obtenerEstadisticosEdadAlumnos () " + mapa_edades);

		return responseEntity;

	}
	
	@GetMapping("/obtenerAlumnosNombreComoProc/{patron}") // GET http://localhost:8081/alumno/obtenerAlumnosNombreComoProc/p
	public ResponseEntity<?> obtenerAlumnosNombreComoProc(@PathVariable String patron ) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en obtenerAlumnosNombreComoProc ()");
			listado_alumnos = this.alumnoService.procedimientoAlumnosNombreComo(patron);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida obtenerAlumnosNombreComoProc () " + listado_alumnos);

		return responseEntity;

	}
	
	@GetMapping("/pagina") // GET http://localhost:8081/alumno/pagina?page=0&size=3
	public ResponseEntity<?> obtenerAlumnosPorPagina(Pageable pageable ) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en obtenerAlumnosPorPagina ()");
			listado_alumnos = this.alumnoService.findAll(pageable);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida obtenerAlumnosPorPagina () " + listado_alumnos);

		return responseEntity;

	}
	
	//http://localhost:8081/alumno/pagina?page=0&size=3&sort=edad,nombre,DESC
	@GetMapping("/paginaOrdenEdad") // GET http://localhost:8081/alumno/pagina?page=0&size=3&sort=edad,ASC
	public ResponseEntity<?> obtenerAlumnosPorPaginaOrdenEdad(Pageable pageable ) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en obtenerAlumnosPorPaginaOrdenEdad ()");
			listado_alumnos = this.alumnoService.findAll(pageable);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			logger.debug("salida obtenerAlumnosPorPaginaOrdenEdad () " + listado_alumnos);

		return responseEntity;

	}
	
	@GetMapping("/paginaOrdenEdadParam") // GET http://localhost:8081/alumno/pagina?page=0&size=3&sort=edad
	public ResponseEntity<?> obtenerAlumnosPorPaginaOrdenEdadParam(
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size,
			@RequestParam(name = "sort", required = false, defaultValue = "edad") String sort) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

			logger.debug("en paginaOrdenEdadParam ()");
			
				 Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
				listado_alumnos = this.alumnoService.findAll(pageable);
				responseEntity = ResponseEntity.ok(listado_alumnos);
				logger.debug("salida paginaOrdenEdadParam () " + listado_alumnos);

		return responseEntity;

	}
	
	
	@GetMapping("/obtenerCursoAlumnoViaFeign/{idalumno}") // GET http://localhost:8081/alumno/obtenerCursoAlumnoViaFeign/1
	public ResponseEntity<?> obtenerCursoAlumnoViaFeign(@PathVariable Long idalumno) throws Exception {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> o_curso = null;

			o_curso = this.alumnoService.obtenerCursoAlumno(idalumno);
			if (o_curso.isPresent()) {
				Curso curso_leido = o_curso.get();
				responseEntity = ResponseEntity.ok(curso_leido);
			} else {
				// no había un alumno con ese ID
				responseEntity = ResponseEntity.noContent().build();
			}

		return responseEntity;

	}


}