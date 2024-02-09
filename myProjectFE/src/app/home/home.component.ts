import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable, catchError, throwError, switchMap, of} from "rxjs";
import {PersonService} from "../service/person.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  name: any;
  surname: any;
  uuid: any;
  role: any;
  groupName: any;
  userLists: any[] = [];

  constructor(private builder: FormBuilder, private toastr: ToastrService, private authService: AuthService, private router: Router, private activateRouter: ActivatedRoute, private personService: PersonService) {
  }

  proceedLogout() {
    sessionStorage.clear()
    this.router.navigate(['login']);
  }

  ngOnInit() {
    this.authService.isUserLogin(this.authService, this.router)

    const prepareList = (role: any) => {
      let api: Observable<Object> | null = null; // Initialize api with null

      if (role === 'ADMIN') {
        api = this.personService.getAll();
      } else if (role === 'LECTURER') {
        api = this.personService.getByLecturer(this.groupName);
      }

      if (api) {
        api.pipe(
          switchMap((res: any) => {
            let groupedPersons: any = {}; // Initialize as an object
            res.forEach((person: { group: { groupName: any }; role: { personCis: string } }) => {
              const groupName = person.group ? person.group.groupName : null; // Check if group is null or undefined
              if (!groupedPersons[groupName]) {
                groupedPersons[groupName] = {
                  LECTURER: [],
                  STUDENT: []
                };
              }

              const group = groupedPersons[groupName];
              if (person.role.personCis === 'LECTURER') {
                group.LECTURER.push(person);
              } else if (person.role.personCis === 'STUDENT') {
                group.STUDENT.push(person);
              }
            });

            this.userLists = Object.keys(groupedPersons).map(groupName => {
              return groupedPersons[groupName];
            });

            return of(null); // Emit a value to indicate completion
          }),
          catchError((error) => {
            return throwError(error);
          })
        ).subscribe(() => {
          // Continue with any additional logic here
        });
      }
    }

    this.activateRouter.queryParams.subscribe(params => {
      this.uuid = params['person'];
      if (this.uuid) {
        this.personService.getPersonByUuid(this.uuid)
          .pipe(
            catchError((error) => {
              // Handle the error here, e.g., display a message to the user
              this.toastr.warning("Bad login");
              // Return an observable to replace the error with a new value or rethrow it
              return throwError(error);
            })
          )
          .subscribe((res: any) => {
            this.name = res.name;
            this.surname = res.surname;
            this.role = res.role.personCis;
            this.groupName = res.group?.groupName;
            prepareList(this.role);
          });
      }
    });
  }


}
