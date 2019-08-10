import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KeywordviewRoutingModule } from './keywordview-routing.module';
import { KeywordviewComponent } from './keywordview.component';
import { KeywordupdateComponent } from '../keywordupdate/keywordupdate.component';
import { KeywordinsertComponent } from '../keywordinsert/keywordinsert.component';
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
    KeywordviewComponent,
    KeywordupdateComponent,
    KeywordinsertComponent
  ],
  imports: [
    CommonModule,
    KeywordviewRoutingModule,
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
    KeywordviewComponent,
    KeywordupdateComponent,
    KeywordinsertComponent
  ]
})
export class KeywordviewModule { }
