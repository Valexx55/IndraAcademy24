package edu.indra.alumnos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.indra.alumnos.repository.AlumnoRepository;
import edu.indra.alumnos.repository.entity.Alumno;

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	@Autowired
	AlumnoRepository alumnoRepository;

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

}
