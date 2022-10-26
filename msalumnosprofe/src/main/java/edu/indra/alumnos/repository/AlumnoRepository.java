package edu.indra.alumnos.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
		
		//4 BUSQUEDA DE ALUMNOS POR NOMBRE O APELLIDO
		
		@Query("SELECT a FROM Alumno a WHERE a.nombre LIKE %?1% OR a.apellido LIKE %?1%")
		public Iterable<Alumno> busquedaPorNombreOApellidoJPQL (String patron);
		
	
	//3 NATIVAS
		
		@Query(value =  "SELECT * FROM alumnos a WHERE a.nombre LIKE %?1% OR a.apellido LIKE %?1%", nativeQuery = true)
		public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
	//4 PROCEDIMIENTOS ALMACENADOS 
		
		//1 DEFINIRLOS EN BASE DE DATOS X
		//2 REFERENCIARLOS EN LA ENTIDAD ALUMNO como para invocarlos desde JAVA x
		//3 HACER MÉTODOS EN ALUMNO REPOSITORY @PROCEDURE que referencian al 2 x
		
		@Procedure(name="Alumno.alumnosRegistradosHoy")
		public Iterable<Alumno> procedimientoAlumnosAltaHoy();
		
		@Procedure(name="Alumno.alumnosEdadMediaMinMax")
		public Map<String, Number> procedimientoEstadisticosEdad(int edadmax, int edadmin, float edadmedia);
		
		@Procedure(name="Alumno.alumnosNombreComo")
		public Iterable<Alumno> procedimientoAlumnosNombreComo(@Param("patron") String patron);
	
	//5 CRITERIA API x
	
	//6 PAGINACIÓN - CONSULTAS

}