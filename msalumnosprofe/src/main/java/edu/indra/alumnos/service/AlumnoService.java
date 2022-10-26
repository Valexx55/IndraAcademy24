package edu.indra.alumnos.service;

import java.util.Optional;

import edu.indra.alumnos.dto.FraseChuckNorris;
import edu.indra.alumnos.repository.entity.Alumno;

public interface AlumnoService {
	
	//ABMC ALUMNOS
	
	public Iterable<Alumno> findAll ();
	
	public Optional<Alumno> findById (Long id);
	
	public Alumno save (Alumno alumno);
	
	public void deleteById (Long id);
	
	public Optional<Alumno> update (Alumno alumno, Long id);
	
	public Optional<FraseChuckNorris> obtenerFraseAleatoriaChuckNorris ();
	
	public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max);
	
	public Iterable<Alumno> findByNombreLike(String buscar);
	
	public Iterable<Alumno> findByNombreContaining(String cadena);

}