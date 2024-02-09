import { Component } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../service/auth.service";
import {Router} from "@angular/router";
import {catchError, throwError} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
  constructor(private builder: FormBuilder, private toastr: ToastrService, private authService: AuthService, private router: Router) {

  }

  loginForm = this.builder.group({
    username: this.builder.control('', Validators.required),
    password: this.builder.control('', Validators.required),
  })


  proceedLogin() {
    if (this.loginForm.valid) {

      this.authService.login(this.loginForm.value)
        .pipe(
          catchError((error) => {
            // Handle the error here, e.g., display a message to the user
            this.toastr.warning("Bad login");
            // Return an observable to replace the error with a new value or rethrow it
            return throwError(error);
          })
        )
        .subscribe((res: any) => {
          sessionStorage.setItem('username', <string>this.loginForm.value.username);
          sessionStorage.setItem('password', <string>this.loginForm.value.password);
          this.router.navigate([''], {
            queryParams: { person: res.uuid}
          });
        });
    } else {
      this.toastr.warning('Please enter valid data');
    }
  }

  ngOnInit() {
    this.authService.isUserLogin(this.authService,this.router);
  }

}
