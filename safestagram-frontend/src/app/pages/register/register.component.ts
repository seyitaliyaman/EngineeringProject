import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import RegisterRequestModel from '../../@core/data/register-request.model';
import { AuthService } from '../../@core/mock/auth.service';

@Component({
  selector: 'ngx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: FormGroup = new FormGroup({
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    username: new FormControl(''),
    password: new FormControl(''),
    gender: new FormControl(''),
    birthdate: new FormControl(''),
    email: new FormControl(''),
  });



  subscriptions: Subscription[] = [];

  constructor(private authService: AuthService,private datePipe: DatePipe, private router: Router) { }

  ngOnInit(): void {
  }

  submit() {
    console.log(this.datePipe.transform(new Date(this.form.value['birthdate']), "dd-MM-yyyy"))
    if (this.form.valid) {
      let registerRequest = new RegisterRequestModel;
      registerRequest.firstName = this.form.value['firstname'];
      registerRequest.surname = this.form.value['lastname'];
      registerRequest.birthdate = new Date(this.form.value['birthdate']);
      registerRequest.email = this.form.value['email'];
      registerRequest.username = this.form.value['username'];
      registerRequest.password = this.form.value['password'];
      registerRequest.gender = this.form.value['gender'];


      this.subscriptions.push(this.authService.register(registerRequest).subscribe(res=>{
        console.log(res)
        this.router.navigate(['login']);
      },error=>{
        console.log(error)
      }))
    }
  }


}
