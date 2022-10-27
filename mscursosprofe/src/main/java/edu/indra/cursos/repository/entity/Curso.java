package edu.indra.cursos.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cursos")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//equivale a autoINC en MySql
	private Long id;
	
	private String nombre;
	
	
	@Column(name = "creado_en")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creadoEn;
	
	@PrePersist
	private void generarFechaCreacion ()
	{
		this.creadoEn = new Date();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}

	
	public Curso() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", creadoEn=" + creadoEn + "]";
	}
	
	

}
