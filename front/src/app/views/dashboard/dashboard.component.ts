import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { KeywordModel } from '../../models/keyword/keywordmodel';
import { Router } from '@angular/router';
import { DashboardService } from '../../service/dashboard/dashboard.service';

@Component({
  templateUrl: 'dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public filePath: string;
  public result: boolean;

  listData: MatTableDataSource<any>;

  displayedColumns: string[] = ['lineNumber', 'programStatement', 'sizeFactorComplexityVal', 'controlTypeFactorComplexityVal',
'nestedFactorComplexityVal','inheritanceFactorComplexityVal','totalWeightComplexityVal','programStatmentComplexityVal',
'recursionFactorComplexityVal','complexityOfProgram','sizeFactorTokenList'];

  @ViewChild(MatSort, null) sort: MatSort;
  @ViewChild(MatPaginator, null) paginator: MatPaginator;

  searchKey: string;

  constructor(
    public router: Router,
    public dashboardService: DashboardService

  ) {
    this.filePath = '';
    this.result = false;
  }

  ngOnInit() {
    this._prepare();
  }

  _prepare() {
    this.dashboardList();
  }

  dashboardList() {
    this.dashboardService.getOutput().then(
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


  dashboardListParam() {
    this.dashboardService.getOutputParam(this.filePath).then(
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

  onSubmit() {
    // this.dashboardListParam();
  }

}
