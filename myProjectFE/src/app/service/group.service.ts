import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  accUrl = 'http://localhost:8081/group';
  constructor(private http:HttpClient) {

  }

  getAll(){
    return this.http.get(this.accUrl+'/findAll')
  }
}
