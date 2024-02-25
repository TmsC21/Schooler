import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "./service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PersonService} from "./service/person.service";
import {SharedDataService} from "./service/shared-data.service";
import {UserListComponent} from "./user-list/user-list.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'myProjectFE';
  name: any;
  surname: any;

  constructor( private router: Router,private sharedDataService: SharedDataService,private personService: PersonService) {
  }
  ngOnInit() {
    // Subscribe to name and surname observables
    this.sharedDataService.name$.subscribe(name => this.name = name);
    this.sharedDataService.surname$.subscribe(surname => this.surname = surname);
  }
  proceedLogout() {
    sessionStorage.clear()
    this.personService.stopApiPolling();
    this.router.navigate(['login']);
  }
  reloadPage(): void {
    window.location.reload();
  }

  protected readonly sessionStorage = sessionStorage;
}
