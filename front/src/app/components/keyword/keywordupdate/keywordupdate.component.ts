import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import {KeywordserviceService} from '../../../services/keyword/keywordservice.service';
import {KeywordModel} from '../../../models/keyword/keywordmodel';

@Component({
  selector: 'app-keywordupdate',
  templateUrl: './keywordupdate.component.html',
  styleUrls: ['./keywordupdate.component.scss']
})
export class KeywordupdateComponent implements OnInit {
  @ViewChild('f', null)
  f: NgForm;
  public keywordname: string;
  constructor(
    public keywordService: KeywordserviceService,
    public activeModal: NgbActiveModal,
    public router: Router
  ) {
  }
  @Input() permissionUpdateObject: KeywordModel;
  public model = new KeywordModel('', '' , '', '');
  public errorMessage: string;
  public warningMessage: string;
  public successMessage: string;
  ngOnInit() {
    this.model = this.permissionUpdateObject;
    this.keywordname = 'test';
  }

  onSubmit() {
    console.log('REQUEST : ' + JSON.stringify(this.model));
    this.keywordService.keywordUpdate(this.model.keywordId, this.model).then(
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
      this.router.navigate(['keyword']));
    this.activeModal.close('Modal Closed');
  }

}
