import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/modelo/alumno';
import { ErrorValidacionServidor } from 'src/app/modelo/error-validacion-servidor';
import { AlumnoserviceService } from 'src/app/servicios/alumnoservice.service';

@Component({
  selector: 'app-alumno-form',
  templateUrl: './alumno-form.component.html',
  styleUrls: ['./alumno-form.component.css']
})
export class AlumnoFormComponent implements OnInit {

  fotoSeleccionada : File | null;//de tipo JS UNION TYPE
  //fotoSeleccionada : File;//de tipo JS UNION TYPE
  titulo: string = "FORMULARIO ALUMNO";
  alumno : Alumno = new Alumno (); //alumnno nuevo o en edición

  constructor(private router:Router , 
    private servicioalumno:AlumnoserviceService, 
    private ruta: ActivatedRoute) { }

  ngOnInit(): void {

    
    //TODO_ OBTENER EL ID DE LA RUTA
    this.ruta.paramMap.subscribe (
      param => {
        let id = param.get('id');
        if (id != null)
        {
          console.log("Viene de editar");
          let alumno_serializado = sessionStorage.getItem("alumno_en_edicion");
          if (alumno_serializado!= null)
          {
            console.log("deserilizado ...");
            this.alumno = JSON.parse(alumno_serializado);
          }
          
          //ALTERNATIVA PARA CARGAR DESDE REMOTO EL USUARIO A EDITAR
          /*
          let n = parseInt(id);
          this.servicioalumno.getAlumno(n).subscribe ( respuesta => {
            this.alumno = respuesta.body as Alumno;
          })*/
        } else {
          console.log("Viene de crear");
        }
      }
    )
  }

  crear (a : Alumno)
  {
    if (this.fotoSeleccionada!=null)
    {
      this.crearConFoto(a , this.fotoSeleccionada);

    } 
    else{
      this.crearSinFoto(a);
    }
  }

  crearSinFoto (a : Alumno)
  {
  console.log("insertar alumno SIN FOTO " + a.nombre);
  this.servicioalumno.crear(a).subscribe (
    alumno_nuevo =>{console.log(alumno_nuevo)
    alert("Alumno creado con éxito");
    this.router.navigateByUrl('/alumnos');}, 
    resperror => {
      if (resperror.status == 400)
      {
        console.log("errores en la validación del servidor");
        let lista_errores : Array<ErrorValidacionServidor> = resperror.error;
        let erroralert : string = "";
        lista_errores.forEach (
          ee => {
            erroralert = erroralert+ (ee.field + " " + ee.defaultMessage + " " + ee.objectName+"\n" ) ;
            console.log(ee.field + " " + ee.defaultMessage + " " + ee.objectName);}
        );
            alert("Errores en la validación del servidor \n" + erroralert);
      }
    }
  )

  }
  crearConFoto (a : Alumno, foto: File)
  {
    console.log("insertar alumno CON FOTO " + a.nombre);
    this.servicioalumno.crearConFoto(a, foto).subscribe (
      alumno_nuevo =>{console.log(alumno_nuevo)
      alert("Alumno creado con éxito");
      this.router.navigateByUrl('/alumnospag');}, 
      resperror => {
        if (resperror.status == 400)
        {
          console.log("errores en la validación del servidor");
          let lista_errores : Array<ErrorValidacionServidor> = resperror.error;
          let erroralert : string = "";
          lista_errores.forEach (
            ee => {
              erroralert = erroralert+ (ee.field + " " + ee.defaultMessage + " " + ee.objectName+"\n" ) ;
              console.log(ee.field + " " + ee.defaultMessage + " " + ee.objectName);}
          );
              alert("Errores en la validación del servidor \n" + erroralert);
        }
      }
    )
  }

  editar ()
  {
    if (this.fotoSeleccionada!=null)
    {
      this.editarConFoto(this.fotoSeleccionada);

    } 
    else{
      this.editarSinFoto();
    }
  }

  //TODO: EN LOS MÉTODOS DE EDICIÓN, NO SE VALIDA LA RESPUSTA DEL SERVIDOR. ESTARÍA BIEN DEFINIR @VALID EN EL SERVIDOR Y HACER COMO EN LOS MÉTODOS DE CREAR/POST
  editarConFoto (foto:File)
  {
    console.log("modificar alumno CON FOTO " + this.alumno.nombre);
    this.servicioalumno.actualizarConFoto(this.alumno, foto).subscribe(
      alumnoeditado => { 
        alert ("Alumno modificado Correctamente");
        this.router.navigateByUrl('/alumnospag');
      }
        , error => { 
          alert ("Error al actualizar");
          console.log(error);
        }
    )
  }

  editarSinFoto ()
  {
    console.log("modificar alumno SIN FOTO " + this.alumno.nombre);
    this.servicioalumno.actualizar(this.alumno).subscribe(
      alumnoeditado => { 
        alert ("Alumno modificado Correctamente");
        this.router.navigateByUrl('/alumnos');
      }
        , error => { 
          alert ("Error al actualizar");
          console.log(error);
        }
    )
  }

  ngOnDestroy() {
    console.log("saliendo ... limpio la session storage");
    sessionStorage.clear();
    
  }

  seleccionarFoto(event :any)
  {
    this.fotoSeleccionada = event.target.files[0];
    //comprobamos el tipo mime del adjunto
    if (this.fotoSeleccionada!=null)
    {
      console.log("TIPO ARCHIVO = " +this.fotoSeleccionada.type);
      if (this.fotoSeleccionada.type.indexOf('image')<0)
      {
        alert("El archivo seleccionado debe ser una foto");
        this.fotoSeleccionada = null;//si el tipo no es correcto, que se guarde a null
      }
    }
    
  }

}
