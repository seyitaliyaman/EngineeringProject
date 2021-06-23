package com.safestagram.ws.web.controller;

import com.safestagram.ws.model.TokenModel;
import com.safestagram.ws.model.request.LoginRequest;
import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.security.JwtGenerator;
import com.safestagram.ws.security.JwtValidator;
import com.safestagram.ws.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.context.url}/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtValidator jwtValidator;
    private final JwtGenerator jwtGenerator;
    private final AuthService authService;


    @PostMapping("/register")
    @ApiOperation("Kullanıcı kayıt işlemini gerçekleştirir.")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequest userRegisterRequest){
        authService.register(userRegisterRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation("Kullanıcı giriş işlemini gerçekleştirir")
    public ResponseEntity<TokenModel> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(jwtGenerator.generate(authService.login(loginRequest).getUserAccount()),HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ApiOperation("Kullanıcı çıkış işlemini gerçekleştirir.")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token){
        authService.logout(jwtValidator.validate(token).getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    @ApiOperation("Kullanıcı şifresinin değiştirilmesini sağlar.")
    public ResponseEntity<Void> changePassword(@RequestHeader("Authorization") String token,@RequestParam String password){
        authService.changePassword(jwtValidator.validate(token).getId(),password);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
