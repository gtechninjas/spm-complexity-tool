import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import {OperatorModel} from '../../models/operator/operatormodel';
import {getEndpoint} from '../../utility/constants';

@Injectable({
  providedIn: 'root'
})
export class OperatorserviceService {

  public operator_insert_url: string;
  public operator_list_url: string;
  public operator_update_url: string;
  public operator_delete_url: string;

  constructor(
    private http: HttpClient
  ) {
    this._prepare();
  }

  _prepare() {
    this.operator_insert_url = `${getEndpoint(false)}/operator`;
    this.operator_list_url = `${getEndpoint(false)}/operator`;
    this.operator_update_url = `${getEndpoint(false)}/operator`;
    this.operator_delete_url = `${getEndpoint(false)}/operator`;
  }

  operatorCreate(operator: OperatorModel): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.post(`${this.operator_insert_url}`, operator).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  operatorUpdate(id: string, operator: OperatorModel): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.put(`${this.operator_update_url}/` + id, operator).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  operatorList(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.operator_list_url}`).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  operatorDelete(id: string): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.delete(`${this.operator_delete_url}/` + id).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }
}
