package com.safestagram.ws.web.controller;

import com.safestagram.ws.model.reponse.UserPostResponse;
import com.safestagram.ws.model.request.UpdatePostRequest;
import com.safestagram.ws.model.request.UploadPostRequest;
import com.safestagram.ws.security.JwtValidator;
import com.safestagram.ws.service.ArchiveService;
import com.safestagram.ws.service.UserPostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.context.url}/post")
@CrossOrigin(origins = "*")
public class UserPostController {

    private final JwtValidator jwtValidator;
    private final UserPostService userPostService;
    private final ArchiveService archiveService;

    @GetMapping
    @ApiOperation("Kullanıcının gönderilerini getirir.")
    public ResponseEntity<List<UserPostResponse>> getUserPosts(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userPostService.getUserPosts(jwtValidator.validate(token).getId()), HttpStatus.OK);
    }

    @GetMapping("/userpost/{id}")
    @ApiOperation("Kullanıcının gönderilerini getirir.")
    public ResponseEntity<List<UserPostResponse>> getUserPostsById(@PathVariable("id") String id,@RequestHeader("Authorization") String token){
        jwtValidator.validate(token).getId();
        return new ResponseEntity<>(userPostService.getUserPosts(Long.valueOf(id)), HttpStatus.OK);
    }

    @GetMapping("/timeline")
    @ApiOperation("Kullanıcının anasayfasında takip ettiği kullanıcıların gönderilerinin gösterilmesini sağlar.")
    public ResponseEntity<List<UserPostResponse>> getUserTimeline(@RequestHeader("Authorization") String token){

        return new ResponseEntity<>(userPostService.getTimeline(jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Belirtilen id değeriyle eşleşen kullanıcı gönderisini getirir.")
    public ResponseEntity<UserPostResponse> getPostById(@PathVariable("id") String id,@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userPostService.getPost(Long.valueOf(id),jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @GetMapping("/{userId}/{id}")
    @ApiOperation("Belirtilen kullanıcı id değeriyle eşleşen kullanıcının belirtilen id'deki gönderisini getirir.")
    public ResponseEntity<UserPostResponse>  getUserPostById(@PathVariable("id") String id,@PathVariable("userId") String userId,@RequestHeader("Authorization") String token){

        return new ResponseEntity<>(userPostService.getPostByUser(Long.valueOf(id),Long.valueOf(userId),jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @GetMapping("/liked")
    @ApiOperation("Kullanıcının beğendeği gönderileri getirir.")
    public ResponseEntity<List<UserPostResponse>> getLikedPosts(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userPostService.getUserLikedPosts(jwtValidator.validate(token).getId()),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ApiOperation("Kullanıcının belirtilen id değeriyle eşleşen gönderisini günceller.")
    public ResponseEntity<Void> updatePost(@PathVariable("id") Long id ,@RequestHeader("Authorization") String token, @RequestBody UpdatePostRequest postRequest){
        userPostService.updatePost(id,jwtValidator.validate(token).getId(),postRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Kullanıcının belirtilen id değeriyle eşleşen gönderisini siler.")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id ,@RequestHeader("Authorization") String token){
        userPostService.deletePost(id,jwtValidator.validate(token).getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/like/{id}")
    @ApiOperation("Kullanıcının belirtilen id değeriyle eşleşen gönderiyi beğenip puanlamasını sağlar.")
    public ResponseEntity<Void> likePost(@PathVariable("id") Long id,@RequestHeader("Authorization") String token){
        userPostService.likePost(id,jwtValidator.validate(token).getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/unlike/{id}")
    @ApiOperation("Kullanıcının belirtilen id değeriyle eşleşen gönderiye verdiği puanı ve beğeniyi geri alır.")
    public ResponseEntity<Void> unlikePost(@PathVariable("id") Long id,@RequestHeader("Authorization") String token){
        userPostService.unlikePost(id,jwtValidator.validate(token).getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/add")
    @ApiOperation("Kullanıcının fotoğraf yüklemesini sağlar.")
    public ResponseEntity<Void> addPost(@RequestHeader("Authorization") String token, @ModelAttribute UploadPostRequest post){
        userPostService.addPost(post,jwtValidator.validate(token).getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> getPostAsResource(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = archiveService.getPostAsResource(fileName,archiveService.getPath());
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }




}
