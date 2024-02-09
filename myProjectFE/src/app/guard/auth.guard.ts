import {CanActivateFn, Router} from '@angular/router';
import {inject, Injectable} from "@angular/core";
import {AuthService} from "../service/auth.service";


export const authGuard: CanActivateFn = (route, state) => {
  return inject(AuthService).isUserLogin(inject(AuthService),inject(Router)) ? true :  inject(Router).navigate(['login']);
};
