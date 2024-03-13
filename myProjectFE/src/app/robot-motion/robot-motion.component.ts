import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {interval, Subscription} from 'rxjs';

@Component({
  selector: 'app-robot-motion',
  templateUrl: './robot-motion.component.html',
  styleUrls: ['./robot-motion.component.css'],
})
export class RobotMotionComponent {
  twinData: any;
  subscription: any;
  isSubscribed: boolean = false;
  @Input() uuid: any;
  @Input() azureConnString: any;
  @Input() azureDeviceId: any;

  constructor(private http: HttpClient) {
  }


  fetchData() {
    const url = `http://localhost:8081/azure/getTwinData?uuid=${this.uuid}`;
    const data = {
      azureConnString: this.azureConnString,
      azureDeviceId: this.azureDeviceId
    };

    this.http.post<any>(url, data).subscribe(
      response => {
        this.twinData = response;
      },
      error => {
        console.error('Error fetching data:', error);
      }
    );
  }


  toggleSubscription() {
    if (this.isSubscribed) {
      this.subscription.unsubscribe();
      this.isSubscribed = false;
    } else {
      this.subscription = interval(2000).subscribe(() => {
        this.fetchData();
      });
      this.isSubscribed = true;
    }
  }


}
