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

  public recursionList: any = [];
  public inheritanceList: any = [];
  public controlList: any = [];
  public nestedList: any = [];
  public sizeList: any = [];

  public totalRecursion: number;
  public totalInheritance: number;
  public totalControl: number;
  public totalNested: number;
  public totalSize: number;

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

    this.totalRecursion = 0;
    this.totalInheritance = 0;
    this.totalControl = 0;
    this.totalNested = 0;
    this.totalSize = 0;
  }

  ngOnInit() {
    this._prepare();
  }

  _prepare() {
    this.dashboardList();
    this.getRecursion();
    this.getInheritance();
    this.getControlStructure();
    this.getNestingControlStructure();
    this.getSize();
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

  getRecursion() {
    this.dashboardService.getRecursion().then(
      data => {
        this.recursionList = data;
        console.log('RECURSION : ' + JSON.stringify(this.recursionList));
        let i;
        for ( i = 0 ; i < this.recursionList.length; i++){
          this.totalRecursion += Number(this.recursionList[i]);
        }
      },
      err => {

      }
    )
  }

  getInheritance() {
    this.dashboardService.getInheritance().then(
      data => {
        this.inheritanceList = data;
        console.log('Inheritance : ' + JSON.stringify(this.inheritanceList));
        let i;
        for ( i = 0 ; i < this.inheritanceList.length; i++){
          this.totalInheritance += Number(this.inheritanceList[i]);
        }
      },
      err => {

      }
    )
  }

  getControlStructure() {
    this.dashboardService.getControlStucture().then(
      data => {
        this.controlList = data;
        console.log('CONTROL STRUCTURE : ' + JSON.stringify(this.controlList));
        let i;
        for ( i = 0 ; i < this.controlList.length; i++){
          this.totalControl += Number(this.controlList[i]);
        }
      },
      err => {

      }
    )
  }


  getNestingControlStructure() {
    this.dashboardService.getNestingControlStucture().then(
      data => {
        this.nestedList = data;
        console.log('NESTED : ' + JSON.stringify(this.nestedList));
        let i;
        for ( i = 0 ; i < this.nestedList.length; i++){
          this.totalNested += Number(this.nestedList[i]);
        }
      },
      err => {

      }
    )
  }


  getSize() {
    this.dashboardService.getSize().then(
      data => {
        this.sizeList = data;
        console.log('SIZE : ' + JSON.stringify(this.sizeList));
        let i;
        for ( i = 0 ; i < this.sizeList.length; i++){
          this.totalSize += Number(this.sizeList[i]);
        }
      },
      err => {

      }
    )
  }

}
