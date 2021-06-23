import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import SmallProfileResponse from '../../@core/data/small-profil-response.model';
import UserPostResponse from '../../@core/data/user-post-response.model';
import { PostService } from '../../@core/mock/post.service';

@Component({
  selector: 'ngx-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  data: string[] = ["1","2","3","4","5","1","2","3","4","5","1","2","3","4","5"]

  headers = new HttpHeaders({
    'Authorization': localStorage.getItem('token')});

  userPosts: UserPostResponse[] = [];

  smallProfile: SmallProfileResponse;
    
  constructor(private router: Router, private route: ActivatedRoute, private postService: PostService) { }

  ngOnInit() {
    let userId = this.route.snapshot.paramMap.get('id');
    
    this.postService.getUserPostsById(userId,this.headers).subscribe(res=>{
      this.userPosts = res;
      this.smallProfile = res[0].profile;
      console.log(this.userPosts)
    },error=>{
      console.log(error)
    })
  }


  showPost(id:number){
    this.router.navigate(['pages/post',id,this.smallProfile.id]);
  }
}
