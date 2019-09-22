import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { Router } from '@angular/router';
import { DashboardService } from '../../service/dashboard/dashboard.service';
import { FileLocation } from '../../models/file/file';

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
'recursionFactorComplexityVal', 'complexityOfProgram','sizeFactorTokenList'];

  @ViewChild(MatSort, null) sort: MatSort;
  @ViewChild(MatPaginator, null) paginator: MatPaginator;

  searchKey: string;

  public model = new FileLocation('');

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
    //  this.dashboardList();
    // this.getRecursion();
    // this.getInheritance();
    // this.getControlStructure();
    // this.getNestingControlStructure();
    // this.getSize();
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


  onSearchClear() {
    this.searchKey = '';
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onSubmit() {
    this.model.fileLocation = this.filePath;
    this.dashboardService.getOutputParam(this.model).then(
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

        this.getRecursion();
        this.getInheritance();
        this.getControlStructure();
        this.getNestingControlStructure();
        this.getSize();
        this.result = true;
      },
      err => {
        this.result = false;
        console.log('RESPONSE ERROR : ' + JSON.stringify(err));
      }
    );
  }

  getRecursion() {
    this.model.fileLocation = this.filePath;
    this.dashboardService.getRecursion(this.model).then(
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
    this.model.fileLocation = this.filePath;
    this.dashboardService.getInheritance(this.model).then(
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
    this.model.fileLocation = this.filePath;
    this.dashboardService.getControlStucture(this.model).then(
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
    this.model.fileLocation = this.filePath;
    this.dashboardService.getNestingControlStucture(this.model).then(
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
    this.model.fileLocation = this.filePath;
    this.dashboardService.getSize(this.model).then(
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
    );
  }

  generate() {
    window.print();
  }

}
