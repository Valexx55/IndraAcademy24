package edu.indra.alumnos.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import edu.indra.alumnos.dto.FraseChuckNorris;
import edu.indra.alumnos.repository.entity.Alumno;

public interface AlumnoService {
	
	//ABMC ALUMNOS
	
	public Iterable<Alumno> findAll ();
	
	public Iterable<Alumno> findAll (Pageable pageable);
	
	public Optional<Alumno> findById (Long id);
	
	public Alumno save (Alumno alumno);
	
	public void deleteById (Long id);
	
	public Optional<Alumno> update (Alumno alumno, Long id);
	
	public Optional<FraseChuckNorris> obtenerFraseAleatoriaChuckNorris ();
	
	public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max);
	
	public Iterable<Alumno> findByNombreLike(String buscar);
	
	public Iterable<Alumno> findByNombreContaining(String cadena);
	
	public Iterable<Alumno> busquedaPorNombreOApellidoJPQL (String patron);
	
	public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
	public Iterable<Alumno> procedimientoAlumnosAltaHoy();
	
	public Map<String, Number> procedimientoEstadisticosEdad();
	
	public Iterable<Alumno> procedimientoAlumnosNombreComo(String patron);
	
	public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max, Pageable pageable);


}