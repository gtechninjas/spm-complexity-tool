import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import { DefaultLayoutComponent } from './containers';

export const routes: Routes = [
  {
    path: '',
    component: DefaultLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: () => import('./views/dashboard/dashboard.module').then(m => m.DashboardModule)
      },
      {
        path: 'keyword',
        loadChildren: () => import('./components/keyword/keywordview/keywordview.module').then(m => m.KeywordviewModule)
      },
      {
        path: 'operator',
        loadChildren: () => import('./components/operator/operatorview/operatorview.module').then(m => m.OperatorviewModule)
      }
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
