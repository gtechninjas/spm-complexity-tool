import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ConfirmdialogService} from '../../../services/confirmdialog/confirmdialog.service';
import {OperatorModel} from '../../../models/operator/operatormodel';
import {OperatorserviceService} from '../../../services/operator/operatorservice.service';
import {OperatorupdateComponent} from '../operatorupdate/operatorupdate.component';
import {OperatorinsertComponent} from '../operatorinsert/operatorinsert.component';

@Component({
  selector: 'app-operatorview',
  templateUrl: './operatorview.component.html',
  styleUrls: ['./operatorview.component.scss']
})
export class OperatorviewComponent implements OnInit, OnDestroy {

  constructor(
    private modalService: NgbModal,
    public confirmdialogService: ConfirmdialogService,
    public router: Router,
    public operatorserviceService: OperatorserviceService
  ) { }
  public model = new OperatorModel('', '', '', '', '');
  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['operator', 'operatorType', 'language', 'value', 'action'];
  @ViewChild(MatSort, null) sort: MatSort;
  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  searchKey: string;

  ngOnInit() {
    this._prepare();
  }

  _prepare() {
    this.userpermissionList();
  }

  userpermissionList() {
    this.operatorserviceService.operatorList().then(
      data => {
        console.log('RESPONSE : ' + JSON.stringify(data));
        const list = data.map(item => {
          return {
            ...item
          };
        });
        this.listData = new MatTableDataSource(list);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      },
      err => {
        console.log('RESPONSE ERROR : ' + JSON.stringify(err));
      }
    );
  }

  onSearchClear() {
    this.searchKey = '';
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  openAddModal() {
    const modalRef = this.modalService.open(OperatorinsertComponent);
  }

  update( operator: OperatorModel ) {
    this.model = operator;
    const modalRef = this.modalService.open(OperatorupdateComponent);
    modalRef.componentInstance.permissionUpdateObject = this.model;
  }

  public delete( operator: OperatorModel ) {
    this.confirmdialogService.confirm('Please confirm..', 'Do you really want to delete this record ?')
      .then((confirmed) => {
        if ( confirmed === true ) {
          console.log('REQUEST : ' + JSON.stringify(operator.operatorId));
          this.operatorserviceService.operatorDelete(operator.operatorId).then(
            data => {
              this.userpermissionList();
              console.log('RESPONSE : ' + JSON.stringify(data));
            }
          ).catch(
            err => {
              console.log('RESPONSE ERROR: ' + JSON.stringify(err));
            }
          );
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  ngOnDestroy() {
    this.modalService.dismissAll();
  }
}
