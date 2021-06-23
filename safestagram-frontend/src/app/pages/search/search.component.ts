import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import SmallProfileResponse from '../../@core/data/small-profil-response.model';
import { UserService } from '../../@core/mock/user.service';
import { UserProfileService } from '../../@core/mock/userprofile.service';

@Component({
  selector: 'ngx-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {


  value = '';

  isResult: boolean = false;

  smallProfile: SmallProfileResponse[] = [];

  headers = new HttpHeaders({ 'Authorization': localStorage.getItem("token") });
  constructor(private router: Router, private userProfileService: UserProfileService, private userService: UserService) { }

  ngOnInit() {
  }

  search() {
    this.userProfileService.searchUser(this.headers, this.value).subscribe(res => {
      this.isResult = true;
      this.smallProfile = res;
    }, error => {
      console.log(error)
    })
  }

  follow(id:number){
    
    this.userService.follow(id,this.headers).subscribe(res=>{
      console.log(res);
    },error=>{
      console.log(error)
    })
  }

  unfollow(id:number){

  }

}
