package com.safestagram.ws.web.controller;

import com.safestagram.ws.model.reponse.SmallProfileResponse;
import com.safestagram.ws.model.reponse.UserProfileResponse;
import com.safestagram.ws.model.request.UpdateProfileRequest;
import com.safestagram.ws.security.JwtValidator;
import com.safestagram.ws.service.UserProfileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.context.url}/profile")
@CrossOrigin(origins = "*")
public class UserProfileController {

    private final JwtValidator jwtValidator;
    private final UserProfileService userProfileService;


    @GetMapping
    @ApiOperation("Kullanıcının profil bilgilerini getirir.")
    public ResponseEntity<UserProfileResponse> get(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userProfileService.getProfileResponseById(jwtValidator.validate(token).getId()), HttpStatus.OK);
    }

    @PostMapping("/upload")
    @ApiOperation("Kullanıcının profil fotoğrafı yüklemesini sağlar.")
    public ResponseEntity<Void> uploadProfilePhoto(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token){
        userProfileService.uploadProfilePhoto(jwtValidator.validate(token).getId(),file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiOperation("Kullanıcının profil bilgilerini güncellemesini sağlar.")
    public ResponseEntity<Void> updateProfileDescription(@RequestHeader("Authorization") String token, @RequestBody UpdateProfileRequest updateProfileRequest){
        userProfileService.updateProfileDescription(jwtValidator.validate(token).getId(),updateProfileRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    @ApiOperation("Kullanıcıyı username değerine göre aramayı sağlar.")
    public ResponseEntity<List<SmallProfileResponse>> searchUser(@RequestParam String username, @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userProfileService.search(username),HttpStatus.OK);
    }
}
