package edu.indra.cursos.service;

import java.util.List;
import java.util.Optional;

import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;


public interface CursoService {
	
	
	public Iterable<Curso> findAll ();
	
	public Optional<Curso> findById (Long id);
	
	public Curso save (Curso curso);
	
	public void deleteById (Long id);
	
	public Optional<Curso> update (Curso curso, Long id);
	
	public Optional<Curso> asignarAlumos (List<Alumno> alumnos, Long id_curso);
	
	public Optional<Curso> eliminarAlumno (Alumno alumno, Long id_curso);

}
