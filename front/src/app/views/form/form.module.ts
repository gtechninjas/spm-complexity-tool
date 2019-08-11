import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ChartsModule } from 'ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { FormComponent } from './form.component';
import { FormRoutingModule } from './form-routing.module';

@NgModule({
  imports: [
    FormsModule,
    FormRoutingModule,
    ChartsModule,
    BsDropdownModule,
    ButtonsModule.forRoot()
  ],
  declarations: [ FormComponent ]
})
export class FormModule { }
