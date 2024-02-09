import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  personUrl = 'http://localhost:8081/person';
  constructor(private http:HttpClient) {

  }

  getPersonByUuid(uuid:any){
    return this.http.get(this.personUrl+'/findByUuid/'+uuid)
  }

  getAll(){
    return this.http.get(this.personUrl+'/findAll')
  }

  getByLecturer(groupName:any){
    return this.http.get(this.personUrl+'/findAllBytGroupName/'+groupName)
  }
}
