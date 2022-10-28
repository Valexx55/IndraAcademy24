import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Miinterceptor2Service implements HttpInterceptor {

  constructor() { }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log ("Pasa por el interceptor 2"); //hacemos la transformación inicial
    
    //return next.handle(request);//le dejo que pase
    return next.handle(request).pipe( respuesta => {console.log('volviendo 2 ..'); return respuesta;});//le dejo que pase
  }
}
