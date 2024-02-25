// shared-data.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  private _name = new BehaviorSubject<string>('');
  private _surname = new BehaviorSubject<string>('');

  name$ = this._name.asObservable();
  surname$ = this._surname.asObservable();

  constructor() {}

  setName(name: string) {
    this._name.next(name);
  }

  setSurname(surname: string) {
    this._surname.next(surname);
  }
}
