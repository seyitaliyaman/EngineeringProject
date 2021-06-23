import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { isNullOrUndefined } from 'util';
import * as alertify from "alertifyjs";
import { Subscription } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthService } from '../../@core/mock/auth.service';
import LoginRequestModel from '../../@core/data/login-request.model';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  constructor(private router: Router, private authService: AuthService) { }
  ngOnInit(): void {
   
  }


  submit() {
    if (this.form.valid) {
      

      let loginRequest = new LoginRequestModel;
      loginRequest.username = this.form.value['username'];
      loginRequest.password = this.form.value['password'];

      this.authService.login(loginRequest).subscribe(res=>{
        console.log(res)
        localStorage.setItem("token",res.token);
        this.router.navigate(['pages/timeline']);
      },error=>{
        console.log(error)
      })
    }
  }
}
