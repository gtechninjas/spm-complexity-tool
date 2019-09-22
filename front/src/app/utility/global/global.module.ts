import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material';
import { MatCheckboxModule, MatInputModule } from '@angular/material';
import { FormsModule , ReactiveFormsModule } from '@angular/forms';
import { NgbModule  } from '@ng-bootstrap/ng-bootstrap';
import { MatIconModule } from '@angular/material';
import {ConfirmComponent} from '../../components/confirm/confirm.component';


@NgModule({
  declarations: [
    ConfirmComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
    FormsModule ,
    ReactiveFormsModule,
    NgbModule,
    MatIconModule
  ],
  entryComponents: [
    ConfirmComponent
  ]
})
export class GlobalModule { }
