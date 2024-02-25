import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {interval, Subscription, switchMap} from "rxjs";
import {UserListComponent} from "../user-list/user-list.component";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  personUrl = 'http://localhost:8081/person';
  accUrl = 'http://localhost:8081/acc';
  apiIntervalSubscription: Subscription | undefined;
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

  getActivities(uuids:any){
    return this.http.post(this.accUrl+'/getActivities/',uuids);
  }

  startPolling(uuid:any,isAdmin:any) {
    // Stop any existing interval subscription to prevent multiple concurrent API calls
    this.stopApiPolling();

    if(!isAdmin){
      this.apiIntervalSubscription = interval(60000) // Interval in milliseconds (1 minute = 60000 milliseconds)
        .pipe(
          switchMap(() => this.http.post(this.accUrl+'/logActivity',uuid)) // Make the API call
        )
        .subscribe();
    }else{
      console.log(uuid)
      this.apiIntervalSubscription = interval(5000).subscribe(() => {
        this.getActivities(uuid).subscribe(
          (activities: any) => {
            for (const id in activities) {
              const div = document.getElementById(id);
              if (div) {
                const img = div.querySelector('img');
                if (img) {
                  img.src = activities[id] ? 'assets/images/Logged.svg' : 'assets/images/notLogged.svg';
                }
              }
            }
          },
          (error: any) => {
            console.error('Error occurred while fetching activities:', error);
            // Handle the error
          }
        );
      });
    }

  }

  stopApiPolling() {
    // Unsubscribe from the interval if it's running
    if (this.apiIntervalSubscription) {
      this.apiIntervalSubscription.unsubscribe();
      this.apiIntervalSubscription = undefined;
    }
  }
}
