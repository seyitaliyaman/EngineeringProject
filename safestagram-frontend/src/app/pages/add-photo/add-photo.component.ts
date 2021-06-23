import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import UploadPostRequest from '../../@core/data/upload-post-request.model';
import { PostService } from '../../@core/mock/post.service';

@Component({
  selector: 'ngx-add-photo',
  templateUrl: './add-photo.component.html',
  styleUrls: ['./add-photo.component.scss']
})
export class AddPhotoComponent implements OnInit {

  url: any;

  photo: File;

  headers = new HttpHeaders({
    'Authorization': localStorage.getItem('token')});
  form: FormGroup = new FormGroup({
    description: new FormControl(''),
  });

  constructor(private postService: PostService, private router: Router) { }

  ngOnInit() {
  }



  selectFile(event: any) { //Angular 11, for stricter type
		this.photo = event.target.files[0];
		
		var reader = new FileReader();
		reader.readAsDataURL(event.target.files[0]);
    console.log(this.photo)
		
		reader.onload = (_event) => {
			this.url = reader.result; 
		}
	}

  addPhoto(){
    let uploadPost = new UploadPostRequest;
    uploadPost.postDescription = this.form.value['description'];
    uploadPost.post = this.photo;
    this.postService.addPost(uploadPost,this.headers).subscribe(res=>{
      this.router.navigate(['/pages/timeline'])
    },error=>{
      console.log(error)
    })
  }

}
