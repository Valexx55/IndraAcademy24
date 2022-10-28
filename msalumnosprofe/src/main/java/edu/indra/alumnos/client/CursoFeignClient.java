package edu.indra.alumnos.client;

import java.util.Map;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import edu.indra.comun.entity.Curso;
import feign.Headers;

@FeignClient(name = "mscursos")
public interface CursoFeignClient {
	
	//@Headers({ "username: {username}", "password: {password}" })
	@GetMapping("/curso/obtenerCursoAlumno/{idalumno}") // GET http://localhost:8081/curso/obtenerCursoAlumno/1
	public Optional<Curso> obtenerCursoAlumno(@PathVariable Long idalumno,  @RequestHeader Map<String, String> headerMap);

}
