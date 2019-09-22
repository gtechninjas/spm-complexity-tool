import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OperatorviewComponent } from './operatorview.component';

const routes: Routes = [
  {
    path: '',
    component: OperatorviewComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperatorviewRoutingModule { }
