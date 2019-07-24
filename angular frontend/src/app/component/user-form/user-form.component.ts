import { Component, OnInit } from '@angular/core';
import {User} from '../../user';
import { Router } from '@angular/router';
import { UserService } from '../../shared_service/user.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {
  private user: User;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.user = this.userService.getter();
  }

  processForm() {
    // tslint:disable-next-line:triple-equals
    if (this.user.id == undefined) {
      this.userService.createUser(this.user).subscribe((user) => {
        console.log(user);
        this.router.navigate(['/']);
      }, (error) => {
        console.log(error);
      });
    } else {
        this.userService.updateUser(this.user).subscribe((user) => {
          console.log(user);
          this.router.navigate(['/']);
        }, (error) => {
          console.log(error);
        });
    }
  }

}
