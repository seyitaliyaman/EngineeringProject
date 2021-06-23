import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable()
export class UserService {
    
    constructor(private http: HttpClient) {
        
    }


    public getUser(headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"user",options);
    }

    public getUserById(id:number, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"user/"+id,options);
    }

    public getFollowers(headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"user/followers",options);
    }

    public getFollowings(headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"user/followings",options);
    }

    public getBlockeds(headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"user/blockeds",options);
    }

    public getFollowersById(id:number, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"user/"+id+"/followers",options);
    }

    public getFollowingsById(id:number, headers: HttpHeaders): any{
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"user/"+id+"/followings",options);
    }

    public follow(id: number, headers: HttpHeaders):any{
        let options = {headers:headers}
        return this.http.put(environment.baseApiUrl+"user/follow/"+id,options);
    }

    public unfollow(id: number, headers: HttpHeaders):any{
        let options = {headers:headers}
        return this.http.delete(environment.baseApiUrl+"user/unfollow/"+id,options);
    }

    public block(id: number, headers: HttpHeaders):any{
        let options = {headers:headers}
        return this.http.put(environment.baseApiUrl+"user/block/"+id,options);
    }

    public unblock(id: number, headers: HttpHeaders):any{
        let options = {headers:headers}
        return this.http.delete(environment.baseApiUrl+"user/block/"+id,options);
    }





}