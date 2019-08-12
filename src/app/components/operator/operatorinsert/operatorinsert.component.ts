import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {OperatorserviceService} from '../../../services/operator/operatorservice.service';
import {OperatorModel} from '../../../models/operator/operatormodel';

@Component({
  selector: 'app-operatorinsert',
  templateUrl: './operatorinsert.component.html',
  styleUrls: ['./operatorinsert.component.scss']
})
export class OperatorinsertComponent implements OnInit {
  @ViewChild('f', null)
  f: NgForm;

  public errorMessage: string;
  public warningMessage: string;
  public successMessage: string;

  constructor(
    public operatorService: OperatorserviceService,
    public router: Router,
    public activeModal: NgbActiveModal
  ) { }
  public model = new OperatorModel('', '' , '', '','');

  ngOnInit() {
  }

  resetMessages() {
    this.errorMessage = '';
    this.warningMessage = '';
    this.successMessage = '';
  }

  reset() {
    this.resetMessages();
    this.f.form.markAsPristine();
    this.f.form.markAsUntouched();
    this.f.form.updateValueAndValidity();
    this.model = new OperatorModel('', '' , '', '','');
  }

  onSubmit() {
    console.log('REQUEST : ' + JSON.stringify(this.model));
    this.operatorService.operatorCreate(this.model).then(
      data => {
        this.successMessage = 'SUCCESSFULLY REGISTERED PERMISSION';
        console.log('RESPONSE : ' + JSON.stringify(data));
      }
    ).catch(
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
