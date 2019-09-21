import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { getEndpoint } from '../../utility/constants';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  public output_url: string;
  public output_param_url: string;

  constructor(
    private http: HttpClient
  ) {
    this._prepare();
  }

  _prepare() {
    this.output_url = `${getEndpoint(false)}/complexity/total`;
    this.output_param_url = `${getEndpoint(false)}/complexity/total`;
  }

  getOutput(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.output_url}`).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  getOutputParam(fileLoc: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.output_param_url}/`+ fileLoc).subscribe(
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
