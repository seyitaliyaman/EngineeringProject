import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import UserPostResponse from '../../@core/data/user-post-response.model';
import { PostService } from '../../@core/mock/post.service';

@Component({
  selector: 'ngx-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  headers = new HttpHeaders({
    'Authorization': localStorage.getItem('token')});


  userPost: UserPostResponse;
  constructor(private router: Router, private route: ActivatedRoute, private postService: PostService) { }

  ngOnInit() {
    let postId = this.route.snapshot.paramMap.get('id');
    let userId = this.route.snapshot.paramMap.get('userId')
    this.postService.getUserPostById(postId,userId,this.headers).subscribe(res=>{

      console.log(res);
      this.userPost = res;
    },error=>{


    })

  }

}
