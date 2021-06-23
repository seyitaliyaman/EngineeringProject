import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {environment} from '../../../environments/environment';
import UploadPostRequest from '../data/upload-post-request.model';

@Injectable()
export class PostService {
    
    constructor(private http: HttpClient) {
        
    }

    public getUserPosts(headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"post",options)
    }

    public getUserPostsById(userId, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"post/userpost/"+userId,options)
    }

    public timeline(headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"post/timeline",options);
    }

    public getPostById(id:string, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"post/singlepost/"+id,options)
    }

    public getUserPostById(userId: string, postId: string, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"post/"+userId+"/"+postId,options);
    }

    public getLikedPosts(headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"post/liked",options);
    }

    public updatePost(postId: number, headers: HttpHeaders, updatePost: any): any{
        let options = {headers:headers}
        return this.http.put(environment.baseApiUrl+"post/update/"+postId,updatePost,options);
    }

    public deletePost(postId: number, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.delete(environment.baseApiUrl+"post/delete"+postId,options);
    }

    public likePost(postId: number, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.put(environment.baseApiUrl+"post/like"+postId,options);
    }

    public unlikePost(postId: number, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.delete(environment.baseApiUrl+"post/unlike"+postId,options);
    }

    public addPost(uploadPostRequest: UploadPostRequest, headers: HttpHeaders){
        let options = {headers:headers}

        let formData = new FormData;
        formData.set('postDescription',uploadPostRequest.postDescription);
        formData.set('post',uploadPostRequest.post);
        return this.http.post(environment.baseApiUrl+"post/add",formData,options)
    }

}