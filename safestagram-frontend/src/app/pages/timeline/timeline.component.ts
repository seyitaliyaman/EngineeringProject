import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import UserPostResponse from '../../@core/data/user-post-response.model';
import { PostService } from '../../@core/mock/post.service';

@Component({
  selector: 'ngx-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss']
})
export class TimelineComponent implements OnInit {


  timeline: UserPostResponse[]= [];

  headers = new HttpHeaders({
    'Authorization': localStorage.getItem('token')});

  data: string[] = ["1","2","3","4","5"]
  constructor(private userPostService: PostService, private router: Router) { }

  ngOnInit() {


    this.userPostService.timeline(this.headers).subscribe(res=>{
      this.timeline = res;
      console.log(res)
    })

  }

  goProfile(id:number){
    this.router.navigate(['pages/profile', id])
  }

}
