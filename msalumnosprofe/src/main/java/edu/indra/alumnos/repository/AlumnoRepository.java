package edu.indra.alumnos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.indra.alumnos.repository.entity.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

}