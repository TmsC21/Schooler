import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, throwError} from "rxjs";

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

  login(inputData:any){
    return this.http.post(this.accUrl+'/login',inputData);
  }

  isUserLogin(authService:any,router:any){
    const storedUsername = sessionStorage.getItem('username');
    const storedPassword = sessionStorage.getItem('password');

    if (storedUsername != null && storedPassword != null) {
      const storedLoginForm = {
        username: storedUsername,
        password: storedPassword
      };

      authService.login(storedLoginForm)
        .pipe(
          catchError((error) => {
            router.navigate(['login']);
            return throwError(error);
          })
        )
        .subscribe((res: any) => {
          router.navigate([''], {
            queryParams: { person: res.uuid}
          });
        });
    }else{
      router.navigate(['login']);
    }
    return false;
  }

  updateUser(){

  }
}
