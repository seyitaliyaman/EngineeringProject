import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable()
export class UserProfileService {
    
    constructor(private http: HttpClient) {
        
    }

    public getProfile(headers: HttpHeaders){
        let options = {headers:headers}
        return this.http.get(environment.baseApiUrl+"profile",options);
    }

    public uploadProfilePhoto(photo: any, headers: HttpHeaders){
        let options = {headers:headers}
        return this.http.post(environment.baseApiUrl+"profile/upload",photo,options);
    }

    public updateProfileDescription(profileDescription: any,headers: HttpHeaders){
        let options = {headers:headers}
        return this.http.put(environment.baseApiUrl+"profile/update",profileDescription,options)
    }

    public searchUser(headers: HttpHeaders, username: string): any{
        let options = {headers:headers};
        let param = new FormData;
        param.set('username',username );
        return this.http.post(environment.baseApiUrl+"profile/search",param,options);
    }

}