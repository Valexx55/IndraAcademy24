import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { RUTA_GATEWAY } from 'src/app/config/app';
import { Alumno } from 'src/app/modelo/alumno';
import { AlumnoserviceService } from 'src/app/servicios/alumnoservice.service';

@Component({
  selector: 'app-alumnopagina',
  templateUrl: './alumnopagina.component.html',
  styleUrls: ['./alumnopagina.component.css']
})
export class AlumnopaginaComponent implements OnInit {

  listaAlumnos: Alumno[];
  titulo: string = "LISTADO DE ALUMNOS";
  automatico: boolean;
  idinterval: any;
  ruta_serv_fotos : string = RUTA_GATEWAY+'obtenerFoto/';

  public totalRegistros: number = 0;
  public totalPorPagina: number = 5;
  public paginaActual: number = 0;
  public pageSizeOptions: number  []= [5, 10, 15, 25];
  @ViewChild(MatPaginator) paginador:MatPaginator;

  //INYECCIÓN DE SERVICIOS
  constructor(public servicio: AlumnoserviceService, private router:Router) {
    this.listaAlumnos = [];
    this.automatico = false;
   
  }

  

  ngOnInit(): void {

    this.obtenerPagina ();
   
  }



  obtenerPagina ()
  {
    this.servicio.listarPaginas(this.paginaActual+'' , this.totalPorPagina+'').subscribe
    (
      
        respuesta => {
          this.listaAlumnos= respuesta.content as Alumno[];
          this.totalRegistros = respuesta.totalElements as number;
          console.log ("long lista = " + this.listaAlumnos.length + " total registros " + this.totalRegistros);
        }
      
    )

  }

  paginar (evento :PageEvent)
  {
    this.paginaActual= evento.pageIndex;
    this.totalPorPagina = evento.pageSize;
    this.obtenerPagina();

  }

  

  ngAfterViewInit() {
   
    this.paginador._intl.itemsPerPageLabel = "Registros por página";
    this.paginador._intl.nextPageLabel = "Siguiente ";
    this.paginador._intl.previousPageLabel = "Anterior ";
    this.paginador._intl.firstPageLabel = "Primera página";
    this.paginador._intl.lastPageLabel = "Última página";
  }

  irAEditar(alumno : Alumno)
  {
    //TODO TENGO QUE TRANSITAR PROGRAMÁTICAMENTE  
    //AL FORMULARIO ALUMNO-FORM PASÁNDLE ESTE ALUMNO POR LA MEMORIA DEL NAVEGADOR
    
    let alumno_json = JSON.stringify(alumno);//serializarlo
    console.log(" alumno_json  = " + alumno_json );
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
  //TODO:LLAMAR AL SERVICIO DE JSONP

  programarActualizacionAtutomatica() {
    this.idinterval = setInterval(() => {
      console.log("actualizando ...");
      this.servicio.listar().subscribe
        (
          alumnos => { this.listaAlumnos = alumnos; }
          ,
          error => { console.log(error); alert(error) }
        );
    }, 3000); //this en una f() anónima es el ámbito que lo engloba (superior)
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
      console.log("el usuario no confirma el borrrado");
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

}
