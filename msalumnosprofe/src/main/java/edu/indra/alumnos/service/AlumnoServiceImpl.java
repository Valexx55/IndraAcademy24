package edu.indra.alumnos.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.indra.alumnos.client.CursoFeignClient;
import edu.indra.alumnos.dto.FraseChuckNorris;
import edu.indra.alumnos.repository.AlumnoRepository;
import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	Logger logger = LoggerFactory.getLogger(AlumnoService.class);
	
	@Autowired
	AlumnoRepository alumnoRepository;
	
	@Autowired
	CursoFeignClient cursoFeignClient;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAll() {
		
		return this.alumnoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Alumno> findById(Long id) {
		return this.alumnoRepository.findById(id);
	}

	@Override
	@Transactional
	public Alumno save(Alumno alumno) {
		return this.alumnoRepository.save(alumno);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.alumnoRepository.deleteById(id);
		
	}

	@Override
	@Transactional
	public Optional<Alumno> update(Alumno alumno, Long id) {
		Optional<Alumno> optional = Optional.empty();
		
			//1 leer 
			optional = this.alumnoRepository.findById(id);
			if (optional.isPresent())
			{
				Alumno alumno_leido = optional.get();//PERSISTENCE
				//alumno_leido.setNombre(alumno.getNombre());
				//le actualizo las propiedades
				BeanUtils.copyProperties(alumno, alumno_leido, "id", "creadoEn");
				//this.alumnoRepository.save(alumno_leido);//NO ES NECESARIO por el estado de la Entidad
				optional = Optional.of(alumno_leido);
			}
			//2 modificar
		
		return optional;
	}

	

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max) {
		return this.alumnoRepository.findByEdadBetween(edad_min, edad_max);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findByNombreLike(String buscar){
		return this.alumnoRepository.findByNombreLike("%"+buscar+"%");
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findByNombreContaining(String cadena) {
		return this.alumnoRepository.findByNombreContaining(cadena);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> busquedaPorNombreOApellidoJPQL(String patron) {
		return this.alumnoRepository.busquedaPorNombreOApellidoJPQL(patron);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> busquedaPorNombreOApellidoNativa(String patron) {
		return this.alumnoRepository.busquedaPorNombreOApellidoNativa(patron);
	}

	@Override
	@Transactional //AUNQUE EL PROCEDIMIENTO NO MODIFIQUE EL ESTADO DE LA BASE DE DATOS (SOLO CONSULTA) DEBO INDICAR READONLY A FALSE -peculiaridad de los procedimientos almancenados-
	public Iterable<Alumno> procedimientoAlumnosAltaHoy() {

		return this.alumnoRepository.procedimientoAlumnosAltaHoy();
	}

	@Override
	@Transactional
	public Map<String, Number> procedimientoEstadisticosEdad() {
		
		return this.alumnoRepository.procedimientoEstadisticosEdad(0, 0, 0f);
	}

	@Override
	@Transactional
	public Iterable<Alumno> procedimientoAlumnosNombreComo(String patron) {
		return this.alumnoRepository.procedimientoAlumnosNombreComo("%"+patron+"%");
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findAll(Pageable pageable) {
		return this.alumnoRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max, Pageable pageable) {
		
		return this.alumnoRepository.findByEdadBetween(edad_min, edad_max, pageable);
	}

	@Override
	public Optional<FraseChuckNorris> obtenerFraseAleatoriaChuckNorris() {
		Optional<FraseChuckNorris> optional = Optional.empty();
		FraseChuckNorris fraseChuckNorris = null;
		RestTemplate restTemplate = null;
		
			restTemplate = new RestTemplate();
			fraseChuckNorris = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", FraseChuckNorris.class);
			optional = Optional.of(fraseChuckNorris);
			
		
		return optional;
	}
	
	
	@Override
	public Optional<Curso> obtenerCursoAlumno(Long idalumno) {
		
		Optional<Curso> ocurso = null;
		
			logger.debug("en obtenerCursoAlumno ()");
			logger.debug("LLAMANDO A FEING ...");
			
			Map<String, String> cabeceras = new HashMap<String, String>();
			
			cabeceras.put("nombre", "valentino");
			cabeceras.put("contrasenia", "nomejodasrafa");
			
			ocurso = this.cursoFeignClient.obtenerCursoAlumno(idalumno, cabeceras);
			
			if (ocurso.isPresent())
			{
				logger.debug("Alumno matriculado en el curso " + ocurso.get());
			}else {
				logger.debug("Alumno NO matriculado en Ningún curso");
			}
		
		return ocurso;
		
	}

}