import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OperatorviewRoutingModule } from './operatorview-routing.module';
import { OperatorviewComponent } from './operatorview.component';
import { OperatorupdateComponent } from '../operatorupdate/operatorupdate.component';
import { OperatorinsertComponent } from '../operatorinsert/operatorinsert.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {
  MatButtonModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatPaginatorModule,
  MatSortModule,
  MatTableModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {GlobalModule} from '../../../utility/global/global.module';
import {ConfirmdialogService} from '../../../services/confirmdialog/confirmdialog.service';

@NgModule({
  declarations: [
    OperatorviewComponent,
    OperatorupdateComponent,
    OperatorinsertComponent
  ],
  imports: [
    CommonModule,
    OperatorviewRoutingModule,
    NgbModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    GlobalModule
  ],
  providers: [
    ConfirmdialogService
  ],
  entryComponents: [
    OperatorviewComponent,
    OperatorupdateComponent,
    OperatorinsertComponent
  ]
})
export class OperatorviewModule { }
