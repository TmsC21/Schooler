import {Component} from '@angular/core';
import {ToastrService} from 'ngx-toastr'

import {FormBuilder, Validators} from '@angular/forms'
import {AuthService} from "../service/auth.service";
import {Router} from "@angular/router";
import {GroupService} from "../service/group.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  groups: any[] = [];

  constructor(private builder: FormBuilder, private toastr: ToastrService, private authService: AuthService, private router: Router,private groupService:GroupService) {
  }

  registerForm = this.builder.group({
    name: this.builder.control('', Validators.required),
    surname: this.builder.control('', Validators.required),
    email: this.builder.control('', Validators.compose([Validators.required, Validators.email])),
    userName: this.builder.control('', Validators.required),
    password: this.builder.control('', Validators.compose([Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')])),
    personCis: this.builder.control('STUDENT'),
    groupName: this.builder.control('', Validators.required),
  })

  ngOnInit() {
    this.groupService.getAll().subscribe(
      (response: any) => {
        this.groups = response; // Assuming the API response contains an array of groups.
      },
      (error) => {
        console.error('Error fetching groups:', error);
        // Handle error if needed
      }
    );
  }



  proceedRegistration() {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe(res =>{
        this.toastr.success('Registered successfully');
        this.router.navigate(['login']);
      });
    } else {
      this.toastr.warning('Please enter valid data')
    }
  }
}
