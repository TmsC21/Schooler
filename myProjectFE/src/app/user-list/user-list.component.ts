import {Component, Input, QueryList, SimpleChanges, ViewChildren} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PersonService} from "../service/person.service";
import {interval, Subscription} from "rxjs";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent {
  @Input() myUserList!: any[];
  @Input() role!: any;

  displayedColumns: string[] = [ 'name', 'surname', 'email'];
  isChecked= false;
  apiIntervalSubscription: Subscription | undefined;
  @ViewChildren('dynamicDivs') dynamicDivs!: QueryList<any>;

  constructor(private builder: FormBuilder, private toastr: ToastrService, private authService: AuthService, private router: Router, private activateRouter: ActivatedRoute, private personService: PersonService) {
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['role'] && this.role !== undefined && changes['myUserList'] && this.myUserList !== undefined) {
      console.log(this.myUserList);
    }
  }

  onSlideToggleChange(event: any) {
    if (event.checked) {
      setTimeout(() => {
        const ids = this.dynamicDivs.map(div => div.nativeElement.id);
        this.personService.startPolling(ids, true);
      }, 0);
    } else {
      this.personService.stopApiPolling();
    }
  }
}
