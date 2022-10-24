package edu.indra.alumnos.service;

import java.util.Optional;

import edu.indra.alumnos.repository.entity.Alumno;

public interface AlumnoService {
	
	//ABMC ALUMNOS
	
	public Iterable<Alumno> findAll ();
	
	public Optional<Alumno> findById (Long id);
	
	public Alumno save (Alumno alumno);
	
	public void deleteById (Long id);
	
	public Optional<Alumno> update (Alumno alumno, Long id);

}
