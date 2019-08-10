import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import {KeywordModel} from '../../models/keyword/keywordmodel';
import {getEndpoint} from '../../utility/constants';

@Injectable({
  providedIn: 'root'
})
export class KeywordserviceService {

  public keyword_insert_url: string;
  public keyword_list_url: string;
  public keyword_update_url: string;
  public keyword_delete_url: string;

  constructor(
    private http: HttpClient
  ) {
    this._prepare();
  }

  _prepare() {
    this.keyword_insert_url = `${getEndpoint(false)}/keyword`;
    this.keyword_list_url = `${getEndpoint(false)}/keyword`;
    this.keyword_update_url = `${getEndpoint(false)}/keyword`;
    this.keyword_delete_url = `${getEndpoint(false)}/keyword`;
  }

  keywordCreate(keyword: KeywordModel): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.post(`${this.keyword_insert_url}`, keyword).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  keywordUpdate(id: string, keyword: KeywordModel): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.put(`${this.keyword_update_url}/` + id, keyword).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  keywordList(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.get(`${this.keyword_list_url}`).subscribe(
        async data => {
          return await resolve(data);
        },
        err => {
          return reject(err);
        }
      );
    });
  }

  keywordDelete(id: string): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http.delete(`${this.keyword_delete_url}/` + id).subscribe(
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
