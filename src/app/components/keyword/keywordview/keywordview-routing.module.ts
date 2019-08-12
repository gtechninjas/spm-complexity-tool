import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { KeywordviewComponent } from './keywordview.component';

const routes: Routes = [
  {
    path: '',
    component: KeywordviewComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KeywordviewRoutingModule { }
