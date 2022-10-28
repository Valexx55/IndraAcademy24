import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Alumno } from 'src/app/modelo/alumno';
import { AlumnoserviceService } from 'src/app/servicios/alumnoservice.service';

@Component({
  selector: 'app-alumno',
  templateUrl: './alumno.component.html',
  styleUrls: ['./alumno.component.css'],
  // providers: [AlumnoserviceService]
})
//COMPONENTE DEDICADO A LISTAR LOS ALUMNOS
export class AlumnoComponent implements OnInit {

  listaAlumnos: Alumno[];
  titulo: string = "LISTADO DE ALUMNOS";
  automatico: boolean;
  idinterval: any;

  //INYECCIÓN DE SERVICIOS
  constructor(public servicio: AlumnoserviceService, private router: Router) {
    this.listaAlumnos = [];
    this.automatico = false;
  }

  ngOnInit(): void {



    //EJEMPLO DE CARGA DE UNA LISTA REMOTA, CON ACCESO A LAS CABECERAS DEL MENSAJE DE RESPUESTA
    this.servicio.listarConHttpCompleto().subscribe(
      httpresp => {
        console.log(httpresp.status);
        this.listaAlumnos = <Alumno[]>httpresp.body;//casting
        this.listaAlumnos.forEach(al => console.log(al));//llama a toString automáticamente otra fomra sería usar JSON stringify

      }
      , fallo => { alert("Fallo del servidor"); console.error(fallo) });
    //FIN EJEMPLO DE CARGA DE UNA LISTA REMOTA, CON ACCESO A LAS CABECERAS DEL MENSAJE DE RESPUESTA


    //EJEMPLO DE CARGA DE UNA LISTA LOCAL, SIN SERVICIO
    /*let a1 : Alumno;
    a1 = new Alumno();

    a1.apellido = "Messi";
    a1.nombre = "Leo";
    a1.email = "leo@mail.es"
    a1.id = 5;

    this.listaAlumnos.push(a1);
    this.listaAlumnos.push(a1);*/
    //FIN EJEMPLO DE CARGA DE UNA LISTA LOCAL, SIN SERVICIO

    //EJEMPLO DE CARGA DE UNA LISTA REMOTA, SIN ACCESO A LAS CABECERAS DEL MENSAJE DE RESPUESTA
    /*this.servicio.listar().subscribe(
      alumnos => {
        console.log(alumnos);
        this.listaAlumnos = alumnos;
      }
      , fallo => {alert ("Fallo del servidor"); console.error(fallo)});*/
    //FIN EJEMPLO DE CARGA DE UNA LISTA REMOTA, SIN ACCESO A LAS CABECERAS DEL MENSAJE DE RESPUESTA

    //EJEMPLO DE CARGA DE UNA LISTA REMOTA, CON AUTENTICACIÓN BÁSICA SIN ACCESO A LAS CABECERAS DEL MENSAJE DE RESPUESTA
    /*this.servicio.listarCifrado().subscribe(
      alumnos => {
        console.log(alumnos);
        this.listaAlumnos = alumnos;
      }
      , fallo => {alert ("Fallo del servidor"); console.error(fallo)});*/
    //FIN EJEMPLO DE CARGA DE UNA LISTA REMOTA, CON AUTENTICACIÓN BÁSICA SIN ACCESO A LAS CABECERAS DEL MENSAJE DE RESPUESTA



    //EJEMPLO DE CARGA DE UNA LISTA REMOTA, MEDIANTE JSONP, PARA EVITAR EL CORS
    //  this.servicio.listarJsonp().subscribe (
    //     respuesta => {
    //       console.log("respuesta JSONP servidor");
    //       console.log(respuesta);
    //       let alumno:Alumno = <Alumno>respuesta;
    //       let alumno1:Alumno = respuesta as Alumno;

    //       console.log("Apellido = " + alumno.apellido);
    //       console.log("Apellido 1= " + alumno1.apellido);
    //       this.listaAlumnos.push(alumno);

    //     }

    //   )

    //FIN EJEMPLO DE CARGA DE UNA LISTA REMOTA, MEDIANTE JSONP, PARA EVITAR EL CORS
  }

  irAEditar(alumno: Alumno) {
    //TODO TENGO QUE TRANSITAR PROGRAMÁTICAMENTE  
    //AL FORMULARIO ALUMNO-FORM PASÁNDLE ESTE ALUMNO POR LA MEMORIA DEL NAVEGADOR

    let alumno_json = JSON.stringify(alumno);//serializarlo
    console.log(" alumno_json  = " + alumno_json);
    sessionStorage.setItem("alumno_en_edicion", alumno_json);
    this.router.navigate(['/alumnos/form', alumno.id]);


  }

  checkTocado() {

    this.automatico = !this.automatico;
    console.log("check tocado auto = " + this.automatico);
    if (this.automatico) {
      this.programarActualizacionAtutomatica();
    } else {
      this.desprogramarActualizacionAutomatica();
    }
  }
  

  programarActualizacionAtutomatica() {
    this.idinterval = setInterval(() => {
      console.log("actualizando ...");
      this.servicio.listar().subscribe
        (
          alumnos => { this.listaAlumnos = alumnos; }
          ,
          error => { console.log(error); alert(error) }
        );
    }, 3000); 
    //this en una f() anónima es el ámbito que lo engloba (superior)
    //this.idinterval = setInterval ( function () { console.log("actualizando ...");}, 3000);
  }


  eliminar(alumno: Alumno): void {
    console.log("quiere eliminar a " + alumno.nombre);
    if (confirm("¿Quieres eliminar a " + alumno.nombre)) {
      //sí
      this.servicio.eliminarCifrado(alumno.id).subscribe(() => {
        //ACTUALIZAR LA LISTA
        this.listaAlumnos = this.listaAlumnos.filter(a => a.id != alumno.id);
        alert("Alumno elminado con éxito");
      }, error => { console.error(error); })
    } else {
      //no quiere elininarlo
      console.log("el usuario no confirma el borrado");
    }
  }

  desprogramarActualizacionAutomatica() {
    clearInterval(this.idinterval);
  }

  ngOnDestroy() {
    console.log("saliendo ...");
    if (this.idinterval) {
      clearInterval(this.idinterval);
    }
  }

  //MÉTODO DE PRUEBA para hacer test sobre él
  helloWorld(): string {
    return "HOla mundo";
  }
}


