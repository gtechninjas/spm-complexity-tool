import { Component, OnInit, ViewChild, Input  } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import {OperatorserviceService} from '../../../services/operator/operatorservice.service';
import {OperatorModel} from '../../../models/operator/operatormodel';

@Component({
  selector: 'app-operatorupdate',
  templateUrl: './operatorupdate.component.html',
  styleUrls: ['./operatorupdate.component.scss']
})
export class OperatorupdateComponent implements OnInit {
  @ViewChild('f', null)
  f: NgForm;
  constructor(
    public operatorService: OperatorserviceService,
    public activeModal: NgbActiveModal,
    public router: Router
  ) { }
  @Input() permissionUpdateObject: OperatorModel;
  public model = new OperatorModel('', '' , '', '','');
  public errorMessage: string;
  public warningMessage: string;
  public successMessage: string;
  ngOnInit() {
    this.model = this.permissionUpdateObject;
  }

  onSubmit() {
    console.log('REQUEST : ' + JSON.stringify(this.model));
    this.operatorService.operatorUpdate(this.model.operatorId, this.model).then(
      data => {
        console.log('RESPONSE : ' + JSON.stringify(data));
        this.successMessage = 'SUCCESSFULLY UPDATED PERMISSION';
      },
      err => {
        console.log('RESPONSE ERROR : ' + JSON.stringify(err));
      }
    );
  }

  closeModal() {
    this.router.navigateByUrl('/RefrshComponent', { skipLocationChange: true }).then(() =>
      this.router.navigate(['operator']));
    this.activeModal.close('Modal Closed');
  }

}