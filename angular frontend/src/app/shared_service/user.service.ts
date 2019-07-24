import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';

import { Observable } from 'rxjs';
import {map} from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
import { User } from '../user';

@Injectable()
export class UserService {

  private baseUrl = 'http://localhost:8080/api';
  private headers = new Headers({'content-Type': 'application/json'});
  private options = new RequestOptions({headers: this.headers});
  private user: User;

  constructor(private http: Http) { }


  getUsers() {
    return this.http.get(this.baseUrl + '/users', this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  // tslint:disable-next-line:ban-types
  getUser(id: Number) {
    return this.http.get(this.baseUrl + '/user' + id, this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  // tslint:disable-next-line:ban-types
  deleteUser(id: Number) {
    return this.http.delete(this.baseUrl + '/user/' + id, this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  createUser(user: User) {
    return this.http.post(this.baseUrl + '/user', JSON.stringify(user), this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  updateUser(user: User) {
    return this.http.put(this.baseUrl + '/user', JSON.stringify(user), this.options).pipe(map((response: Response) => response.json()))
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: Response) {
    return Observable.throw(error || 'Server Error');
  }

  setter(user: User) {
    this.user = user;
  }

  getter() {
    return this.user;
  }
}
