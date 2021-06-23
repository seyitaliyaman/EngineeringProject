import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {environment} from '../../../environments/environment';
import RegisterRequestModel from '../data/register-request.model';
import LoginRequestModel from '../data/login-request.model';
@Injectable()

export class AuthService {
    
    constructor(private http: HttpClient) {
        
    }

    public register(registerRequest: RegisterRequestModel){
        return this.http.post(environment.baseApiUrl+"auth/register",registerRequest)
    } 

    public login(loginRequest: LoginRequestModel): any{
        return this.http.post(environment.baseApiUrl+"auth/login",loginRequest)
    }

    public logout(headers:HttpHeaders){
        let options = {headers:headers}
        return this.http.post(environment.baseApiUrl+"auth/logout",options)
    }

    public verify(email:string, verificationCode:string, headers:HttpHeaders){
        let options = {headers:headers}
        let param = new FormData;
        param.set('email',email );
        param.set('verificationCode', verificationCode);
        return this.http.post(environment.baseApiUrl+"auth/verify",param,options)
    }

    public sendCode(headers:HttpHeaders){
        let options = {headers:headers}
        return this.http.post(environment.baseApiUrl+"auth/sendCode",options)
    }

    public changePassword(password: string, headers:HttpHeaders){
        let options = {headers:headers}
        return this.http.put(environment.baseApiUrl+"auth/changePassword",options)
    }



    // public login(loginData:LoginModel, headers:HttpHeaders) {
    //     let options = {headers:headers}
    //     return this.http.post(this.loginUrl, loginData, options)
    // }

}