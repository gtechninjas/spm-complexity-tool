import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ConfirmdialogService} from '../../../services/confirmdialog/confirmdialog.service';
import {KeywordModel} from '../../../models/keyword/keywordmodel';
import {KeywordserviceService} from '../../../services/keyword/keywordservice.service';
import {KeywordupdateComponent} from '../keywordupdate/keywordupdate.component';
import {KeywordinsertComponent} from '../keywordinsert/keywordinsert.component';

@Component({
  selector: 'app-keywordview',
  templateUrl: './keywordview.component.html',
  styleUrls: ['./keywordview.component.scss']
})
export class KeywordviewComponent implements OnInit, OnDestroy {

  constructor(
    private modalService: NgbModal,
    public confirmdialogService: ConfirmdialogService,
    public router: Router,
    public keywordserviceService: KeywordserviceService
  ) { }
  public model = new KeywordModel('', '', '', '');
  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['keywordid', 'keyword', 'language', 'value', 'action'];
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
    this.keywordserviceService.keywordList().then(
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
    const modalRef = this.modalService.open(KeywordinsertComponent);
  }

  update( keyword: KeywordModel ) {
    this.model = keyword;
    const modalRef = this.modalService.open(KeywordupdateComponent);
    modalRef.componentInstance.permissionUpdateObject = this.model;
  }

  public delete( keyword: KeywordModel ) {
    this.confirmdialogService.confirm('Please confirm..', 'Do you really want to delete this record ?')
      .then((confirmed) => {
        if ( confirmed === true ) {
          console.log('REQUEST : ' + JSON.stringify(keyword.keywordId));
          this.keywordserviceService.keywordDelete(keyword.keywordId).then(
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
