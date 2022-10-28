import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MiinterceptorService implements HttpInterceptor{

  constructor() { }
  
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log ("Pasa por el interceptor 1"); //hacemos la transformación inicial
    let request = req.clone ({
      setHeaders: 
      {
        miciudad: 'SEBASTOPOL'
      }
    });
    //return next.handle(request);//le dejo que pase
    return next.handle(request).pipe(
      map ((event: HttpEvent<any>) =>
      {
        if (event instanceof HttpResponse)//YA HE LLEGADO LA RESPUESTA
        { 
          this.mostrarCuerpo(event.body);
          event = event.clone ({body: this.modificarCuerpo(event.body)})
        }
        return event;
      })
    );//
  
}
  private mostrarCuerpo (cuerpo:any)
  {
    console.log("cuerpo RX " + cuerpo.toString());
  }

  private modificarCuerpo (cuerpo:any)
  {
    console.log("cuerpo RX " + cuerpo);//AQUÍ PODRÍA MODIFICAR O AÑADIR INFO AL CUERPO DE LA RESPUESTA HTTP
    return cuerpo;
  }
}