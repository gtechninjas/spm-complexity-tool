import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {KeywordserviceService} from '../../../services/keyword/keywordservice.service';
import {KeywordModel} from '../../../models/keyword/keywordmodel';

@Component({
  selector: 'app-keywordinsert',
  templateUrl: './keywordinsert.component.html',
  styleUrls: ['./keywordinsert.component.scss']
})
export class KeywordinsertComponent implements OnInit {
  @ViewChild('f', null)
  f: NgForm;

  public errorMessage: string;
  public warningMessage: string;
  public successMessage: string;

  constructor(
    public keywordService: KeywordserviceService,
    public router: Router,
    public activeModal: NgbActiveModal
  ) { }
  public model = new KeywordModel('', '' , '', '');

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
    this.model = new KeywordModel('', '' , '', '');
  }

  onSubmit() {
    console.log('REQUEST : ' + JSON.stringify(this.model));
    this.keywordService.keywordCreate(this.model).then(
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
      this.router.navigate(['keyword']));
    this.activeModal.close('Modal Closed');
  }
}
