package com.safestagram.ws.web.controller;

import com.safestagram.ws.model.reponse.SmallProfileResponse;
import com.safestagram.ws.model.reponse.UserResponse;
import com.safestagram.ws.security.JwtValidator;
import com.safestagram.ws.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.context.url}/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final JwtValidator jwtValidator;
    private final UserService userService;

    @GetMapping
    @ApiOperation("Kullanıcının kendi profil bilgilerini getirir.")
    public ResponseEntity<UserResponse> get(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userService.get(jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("Verilen id değeriyle eşleşen kullanıcının profil bilgilerini getirir.")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userService.get(id, jwtValidator.validate(token).getId()), HttpStatus.OK);
    }

    @GetMapping(value = "/followers")
    @ApiOperation("Kullanıcının takipçilerini getirir.")
    public ResponseEntity<List<SmallProfileResponse>> getFollowers(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userService.getFollowers(jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @GetMapping(value = "/followings")
    @ApiOperation("Kullanıcının takip ettiği kişileri getirir.")
    public ResponseEntity<List<SmallProfileResponse>> getFollowings(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userService.getFollowings(jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @GetMapping(value = "/blockeds")
    @ApiOperation("Kullanıcının engellediği kişileri getirir.")
    public ResponseEntity<List<SmallProfileResponse>> getBlockeds(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userService.getBlockeds(jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/followers")
    @ApiOperation("Verilen id değeriyle eşleşen kullanıcının takipçilerini getirir.")
    public ResponseEntity<List<SmallProfileResponse>> getUserFollowers(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        jwtValidator.validate(token);
        return new ResponseEntity<>(userService.getFollowers(id),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/followings")
    @ApiOperation("Verilen id değeriyle eşleşen kullanıcının takip ettiklerini getirir.")
    public ResponseEntity<List<SmallProfileResponse>> getUserFollowings(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
        jwtValidator.validate(token);
        return new ResponseEntity<>(userService.getFollowings(id),HttpStatus.OK);
    }

    @PutMapping("/follow/{id}")
    @ApiOperation("Belirtilen id değeriyle eşleşen kullanıcıyı takip eder.")
    public ResponseEntity<Void> follow(@RequestHeader("Authorization") String token, @PathVariable("id") Long id){
        userService.followUser(jwtValidator.validate(token).getId(),id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/unfollow/{id}")
    @ApiOperation("Belirtilen id değeriyle eşleşen kullanıcıyı takipten çıkarır.")
    public ResponseEntity<Void> unfollow(@RequestHeader("Authorization") String token, @PathVariable("id") Long id){
        userService.unfollowUser(jwtValidator.validate(token).getId(),id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/block/{id}")
    @ApiOperation("Belirtilen id değeriyle eşleşen kullanıcıyı engeller.")
    public ResponseEntity<Void> block(@RequestHeader("Authorization") String token, @PathVariable("id") Long id){
        userService.blockUser(jwtValidator.validate(token).getId(),id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/unblock/{id}")
    @ApiOperation("Belirtilen id değeriyle eşleşen kullanıcının engelini kaldırır.")
    public ResponseEntity<Void> unblock(@RequestHeader("Authorization") String token, @PathVariable("id") Long id){
        userService.unblockUser(jwtValidator.validate(token).getId(),id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ApiOperation("Kullanıcı hesabını silmeyi sağlar.")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token){
        userService.deleteUser(jwtValidator.validate(token).getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
