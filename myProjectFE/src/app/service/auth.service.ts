import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  personUrl = 'http://localhost:8081/person';
  accUrl = 'http://localhost:8081/acc';
  constructor(private http:HttpClient) {

  }

  getAll(){
    return this.http.get(this.personUrl+'/findAll')
  }

  getByUserName(userName:any){
    return this.http.get(this.personUrl+'/findByUserName/'+userName)
  }

  register(inputData:any){
    return this.http.post(this.accUrl+'/register',inputData);
  }

  updateUser(){

  }
}
