import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RUTA_GATEWAY, RUTA_MS_ALUMNOS } from '../config/app';
import { Alumno } from '../modelo/alumno';

@Injectable({
  providedIn: 'root'//que se pueda usar desde cualquier componente
})
export class AlumnoserviceService {

  //ruta_servidor : string = "http://localhost:8080"
  ruta_servidor : string = RUTA_GATEWAY; //"http://localhost:8090/api/alumnos/"

  cabeceras : HttpHeaders = new HttpHeaders ({'Content-type' : 'application/json'});
  cabecerascifradas : HttpHeaders = new HttpHeaders ({'Authorization' : 'Basic ' + btoa('admin:admin')});


  constructor(private http: HttpClient) {

    
   }


   //TODO LISTAR UN ALUMNO BUSCAR POR UN ALUMNO

   //mecanimos asincronos AJAX
   //Promesas
   //Observables - mejor REACTIVA -rxjs - APACHE
    


   public getAlumno(id: number) : Observable<HttpResponse<Alumno>>{
    return this.http.get<Alumno>(`${this.ruta_servidor}${id}`,{ observe: 'response' });
  }

  //obtener lista de alumnos
   public listar () : Observable<Alumno[]>
    {
      console.log("Obteninedo lista de todos los alumnos");
      return this.http.get<Alumno[]>(this.ruta_servidor);
    }
//obtener lista de alumnos con cabeceras
    public listarConHttpCompleto () : Observable<HttpResponse<Alumno[]>>
    {
      return this.http.get<Alumno[]>(this.ruta_servidor, { observe: 'response' });
    }

    public listarCifrado () : Observable<Alumno[]>
    {
      return this.http.get<Alumno[]>(this.ruta_servidor, {headers : this.cabecerascifradas});
    }

    public crear (alumno : Alumno) : Observable<Alumno>
    {
      return this.http.post<Alumno>(this.ruta_servidor, alumno, {headers : this.cabeceras});
    }

    public actualizar (alumno : Alumno) : Observable<Alumno>
    {
      return this.http.put<Alumno>(this.ruta_servidor+""+alumno.id, alumno, {headers : this.cabeceras});
    }

     //TODO: este método realmente no se invoca. se ha definido seguridad sobre él en el servidor por lo que se invoca a eliminarCifrado
    public eliminar (id: number): Observable<void>
    {
      //return this.http.delete<void> (this.ruta_servidor+"/"+id);
      return this.http.delete<void> (`${this.ruta_servidor}${id}`);
    }

    
    public eliminarCifrado (id: number) : Observable<void>
    {
      return this.http.delete<void>(`${this.ruta_servidor}${id}`, {headers : this.cabecerascifradas});
    }

    public listarJsonp ()
    {
      return this.http.jsonp(`${this.ruta_servidor}jsonp/alumno`, 'callback=JSONP_CALLBACK');
    }

    public listarPaginas (page:string, size:string): Observable<any>
    {
      let parametros = new HttpParams().set('page', page).set('size', size);
      return this.http.get<any>(`${this.ruta_servidor}pagina`, {params:parametros});

    }
    
    public crearConFoto (alumno : Alumno, archivo: File):Observable<Alumno>
    {
      let formData = new FormData();//el cuerpo de la petición multipart FILE
      formData.append('archivo', archivo);
      formData.append('nombre', alumno.nombre);
      formData.append('apellido', alumno.apellido);
      formData.append('edad', alumno.edad+"");
      formData.append('email', alumno.email);

      return this.http.post<Alumno>(`${this.ruta_servidor}crear-con-foto`, formData);
      //return this.http.post<Alumno>(this.ruta_servidor + "/crear-con-foto", formData);

    }

    public actualizarConFoto (alumno : Alumno, archivo: File):Observable<Alumno>
    {
      let formData = new FormData();//el cuerpo de la petición multipart FILE
      formData.append('archivo', archivo);
      formData.append('nombre', alumno.nombre);
      formData.append('apellido', alumno.apellido);
      formData.append('edad', alumno.edad+"");
      formData.append('email', alumno.email);

      return this.http.put<Alumno>(`${this.ruta_servidor}editar-con-foto/${alumno.id}`, formData);

    }
}
