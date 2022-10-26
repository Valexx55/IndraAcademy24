package edu.indra.alumnos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.indra.alumnos.repository.entity.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
	
	//1 KEY WORD QUERIES
	
		//1 OBTENER UN LISTADO DE ALUMNOS EN UN RANGO DE EDAD
		public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max);
		
		//2 OBTENER UN LISTADO DE ALUMNOS CUYO NOMBRE CUMPLA UN PATRÓN con like (hay que concatenar los comodines %)
		public Iterable<Alumno> findByNombreLike(String buscar);
		
		//3 OBTENER UN LISTADO DE ALUMNOS CUYO NOMBRE CUMPLA UN PATRÓN con Containing (NO HACE FALTA concatenar los comodines %)
		public Iterable<Alumno> findByNombreContaining(String cadena);
	
	//2 JPQL - "Agnóstico"
	
	//3 NATIVAS
	
	//4 PROCEDIMIENTOS ALMACENADOS 
	
	//5 CRITERIA API x
	
	//6 PAGINACIÓN - CONSULTAS

}