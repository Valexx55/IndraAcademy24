import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenunavbarComponent } from './menunavbar/menunavbar.component';
import { AppRoutingModule } from '../app-routing.module';



@NgModule({
  declarations: [
    MenunavbarComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule
  ], 
  exports: [MenunavbarComponent]
})
export class LayoutModule { }
