package edu.indra.comun.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.PrePersist;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "alumnos")
@NamedStoredProcedureQueries(
		{
			@NamedStoredProcedureQuery(name="Alumno.alumnosRegistradosHoy", procedureName = "obtenerAlumnosRegistradosHoy", resultClasses = edu.indra.comun.entity.Alumno.class),
			@NamedStoredProcedureQuery(name="Alumno.alumnosEdadMediaMinMax", procedureName = "calcular_max_min_media_edad",
			parameters = {
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmax"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmin"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Float.class , name ="edadmedia")
			}),
			@NamedStoredProcedureQuery(name="Alumno.alumnosNombreComo", procedureName = "obtenerAlumnosConNombreComo", 
			parameters = {
					@StoredProcedureParameter(mode = ParameterMode.IN , type = String.class , name ="patron")
			}
			,resultClasses = edu.indra.comun.entity.Alumno.class)
		}
		)
public class Alumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//equivale a autoINC en MySql
	private Long id;
	
	@Size(min = 3, max = 20)
	private String nombre;
	
	@NotEmpty//apellido tenga longitud >1
	private String apellido;
	
	@Email
	private String email;
	
	@Min(0)
	@Max(130)
	private int edad;
	
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}

	public Alumno(Long id, String nombre, String apellido, String email, int edad, Date creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.creadoEn = creadoEn;
	}
	
	public Alumno() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", edad="
				+ edad + ", creadoEn=" + creadoEn + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean iguales = false;
		
			if (this == obj)
			{
				iguales = true;
			} else if (obj instanceof Alumno) //if (obj instanceof Alumno a)//https://docs.oracle.com/en/java/javase/14/language/pattern-matching-instanceof-operator.html
			{
				Alumno a = (Alumno)obj;
				Objects.equals(this.id, a.id);
				iguales = ((this.id != null)&&(this.id.equals(a.id)));//Objects.equals(this.id, a.id);
			}
		
		return iguales;
	}
	

}