import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AlumnoFormComponent } from './componentes/alumno/alumno-form.component';
import { AlumnoComponent } from './componentes/alumno/alumno.component';
import { AlumnopaginaComponent } from './componentes/alumno/alumnopagina.component';

const routes: Routes = [
  {path:"alumnos", component: AlumnoComponent},
  {path:"alumnospag", component: AlumnopaginaComponent},
  {path:"alumnos/form", component: AlumnoFormComponent},
  {path:"alumnos/form/:id", component: AlumnoFormComponent}
];

//TODO linkar el componente con el menú de appcomponent.html
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
