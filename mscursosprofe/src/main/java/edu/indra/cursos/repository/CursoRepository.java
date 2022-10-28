package edu.indra.cursos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.indra.comun.entity.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

}
