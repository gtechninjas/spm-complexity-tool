import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { getEndpoint } from '../../utility/constants';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  public output_url: string;
  public output_param_url: string;
  public recursion_url: string;
  public inheritance_url: string;
  public control_factor_url: string;
  public nested_control_factor_url: string;
  public complexity_size_url: string;

  constructor(
    private http: HttpClient
  ) {
    this._prepare();
  }

  _prepare() {
    this.output_url = `${getEndpoint(false)}/complexity/total`;
    this.output_param_url = `${getEndpoint(false)}/complexity/total`;
    this.recursion_url = `${getEndpoint(false)}/complexity/recursion`;
    this.inheritance_url = `${getEndpoint(false)}/complexity/inheritance`;
    this.control_factor_url = `${getEndpoint(false)}/complexity/controltype`;
    this.nested_control_factor_url = `${getEndpoint(false)}/complexity/nested`;
    this.complexity_size_url = `${getEndpoint(false)}/complexity/size`;
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

  getRecursion(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.recursion_url}`).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  getInheritance(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.inheritance_url}`).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  getControlStucture(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.control_factor_url}`).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  getNestingControlStucture(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.nested_control_factor_url}`).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  getSize(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.complexity_size_url}`).subscribe(
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
