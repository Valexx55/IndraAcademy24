import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientJsonpModule, HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AlumnoComponent } from './componentes/alumno/alumno.component';
import { LayoutModule } from './layout/layout.module';
import { AlumnoFormComponent } from './componentes/alumno/alumno-form.component';
import { FormsModule } from '@angular/forms';
import { MiinterceptorService } from './servicios/miinterceptor.service';
import { Miinterceptor2Service } from './servicios/miinterceptor2.service';
import { AlumnopaginaComponent } from './componentes/alumno/alumnopagina.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatPaginatorModule} from '@angular/material/paginator';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [
    AppComponent,
    AlumnoComponent,
    AlumnoFormComponent,
    AlumnopaginaComponent, 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    LayoutModule, 
    HttpClientModule,
    HttpClientJsonpModule,
    FormsModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    FontAwesomeModule
  ],
  providers: [
    //aquí puedo definir varios interceptores y su orden declarativo, determina su orden de ejecución
    //parece que no es posible limitar el interceptor a sólo determinados métodos HTTP o Servicios ANgular
    //y se ejecutan para todos
    { provide: HTTP_INTERCEPTORS , useClass: MiinterceptorService, multi: true},
    { provide: HTTP_INTERCEPTORS , useClass: Miinterceptor2Service, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
