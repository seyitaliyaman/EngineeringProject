package com.safestagram.ws.service;

import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.model.reponse.SmallProfileResponse;
import com.safestagram.ws.model.reponse.UserPostResponse;
import com.safestagram.ws.model.request.UpdatePostRequest;
import com.safestagram.ws.model.request.UploadPostRequest;
import com.safestagram.ws.persistence.entity.UserEntity;
import com.safestagram.ws.persistence.entity.UserLikedPostsEntity;
import com.safestagram.ws.persistence.entity.UserPostEntity;
import com.safestagram.ws.persistence.repository.UserPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class UserPostService {

    private final UserPostRepository userPostRepository;
    private final ArchiveService archiveService;
    private final UserService userService;
    private final UserLikedPostsService userLikedPostsService;


    public List<UserPostResponse> getTimeline(Long userId){
        UserEntity user = userService.getById(userId);
        List<UserPostEntity> postList = userPostRepository.findByUserNotOrderByCreateDateDesc(user).orElseGet(ArrayList::new);
        List<UserPostResponse> postResponseList = new ArrayList<>();


        for(UserPostEntity postEntity : postList){
            if(user.getFollowingUsers().contains(postEntity.getUser().getUserProfile())){
                UserPostResponse userPostResponse = toUserPostResponse(postEntity);

                for(UserLikedPostsEntity likedPostsEntity : user.getLikedPosts()){
                    if(likedPostsEntity.getScoredPost().equals(postEntity)){
                        userPostResponse.setIsLiked(true);
                        break;
                    }
                }
                postResponseList.add(userPostResponse);
            }
        }

        return postResponseList;
    }

    public List<UserPostResponse> getUserPosts(Long userId){
        return allToUserPostResponse(userPostRepository.findAllByUserAndIsDeletedOrderById(userService.getById(userId),false).orElseGet(ArrayList::new));
    }

    public UserPostResponse getPost(Long postId, Long userId){
        UserEntity user = userService.getById(userId);
        UserPostEntity post = userPostRepository.findByIdAndUserAndIsDeleted(postId,user,false).orElseThrow(()-> new SafestagramServiceException(ErrorCode.POST_NOT_FOUND));
        UserPostResponse userPost = toUserPostResponse(post);

        for(UserLikedPostsEntity likedPost : user.getLikedPosts()){
            if(likedPost.getScoredPost().equals(post)){
                userPost.setIsLiked(true);
                break;
            }
        }
        return userPost;
    }


    public UserPostResponse getPostByUser(Long postId, Long postUserId, Long userId){
        UserEntity postUser = userService.getById(postUserId);
        UserEntity user = userService.getById(userId);
        UserPostEntity post = userPostRepository.findByIdAndUserAndIsDeleted(postId,postUser,false).orElseThrow(()-> new SafestagramServiceException(ErrorCode.POST_NOT_FOUND));
        UserPostResponse userPost = toUserPostResponse(post);
        for(UserLikedPostsEntity likedPostsEntity : user.getLikedPosts()){
            if(likedPostsEntity.getScoredPost().equals(post)){
                userPost.setIsLiked(true);
                break;
            }
        }
        return userPost;
    }

    public void addPost(UploadPostRequest postRequest, Long userId){
        if(archiveService.storePost(postRequest)){
            UserPostEntity userPost = new UserPostEntity();
            userPost.setCreateDate(new Date());
            userPost.setIsDeleted(false);
            userPost.setPostDescription(postRequest.getPostDescription());
            userPost.setUser(userService.getById(userId));
            userPost.setPostPath(archiveService.getCurrrentArchiveFileName());
            userPost.setPostSize((int) postRequest.getPost().getSize());
            String fileDownloadUri = "http://localhost:8080/api/v1/post/download/"+postRequest.getPost().getOriginalFilename();
            userPost.setPostUrl(fileDownloadUri);


            RestTemplate restTemplate = new RestTemplate();
            String url = "http://127.0.0.1:5000/classify";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> postData = new LinkedMultiValueMap<>();
            postData.add("postPath", userPost.getPostPath());
            HttpEntity<MultiValueMap<String, String>> entity
                    = new HttpEntity<>(postData, headers);
            String postClass = restTemplate.postForEntity(url, postData, String.class).getBody();
            userPost.setPostClass(postClass);
            userPostRepository.save(userPost);
        }else {
            archiveService.removeFile(archiveService.getCurrrentArchiveFileName());
            throw new SafestagramServiceException(ErrorCode.POST_STORE_FAILED);
        }
    }

    public void deletePost(Long postId, Long userId){
        UserPostEntity userPost = userPostRepository.findByIdAndUserAndIsDeleted(postId,userService.getById(userId),false).orElseThrow(()-> new SafestagramServiceException(ErrorCode.POST_NOT_FOUND));
        userPost.setIsDeleted(false);
    }

    public void updatePost( Long postId, Long userId, UpdatePostRequest updatePost){
        UserPostEntity userPost = userPostRepository.findByIdAndUserAndIsDeleted(postId,userService.getById(userId),false).orElseThrow(()-> new SafestagramServiceException(ErrorCode.POST_NOT_FOUND));
        userPost.setPostDescription(updatePost.getPostDescription());
        userPost.setUpdateDate(new Date());
        userPostRepository.save(userPost);
    }

    public void likePost(Long postId, Long likedUserId){
        UserPostEntity userPost = userPostRepository.findById(postId).orElseThrow(()-> new SafestagramServiceException(ErrorCode.POST_NOT_FOUND));
        userPostRepository.save(userPost);
        UserEntity user = userService.getById(likedUserId);
        userService.updateUser(user);
    }

    public void unlikePost(Long postId, Long unlikedUserId){
        UserPostEntity userPost = userPostRepository.findById(postId).orElseThrow(()-> new SafestagramServiceException(ErrorCode.POST_NOT_FOUND));
        UserLikedPostsEntity userLikedPost = userLikedPostsService.getByPost(userPost);
        UserEntity user = userService.getById(unlikedUserId);
        userPostRepository.save(userPost);
        user.getLikedPosts().remove(userLikedPost);
        userService.updateUser(user);
        userLikedPostsService.delete(userLikedPost);

    }

    public List<UserPostResponse> getUserLikedPosts(Long userId){

        UserEntity userEntity = userService.getById(userId);
        List<UserPostResponse> userPostResponses = new ArrayList<>();
        for(UserLikedPostsEntity likedPost : userEntity.getLikedPosts()){
            UserPostResponse userPostResponse = toUserPostResponse(likedPost.getScoredPost());
            userPostResponse.setIsLiked(true);
            userPostResponses.add(userPostResponse);
        }
        return userPostResponses;
    }

    public UserPostResponse toUserPostResponse(UserPostEntity userPostEntity){
        UserPostResponse userPostResponse = new UserPostResponse();

        SmallProfileResponse smallProfileResponse = new SmallProfileResponse();
        smallProfileResponse.setId(userPostEntity.getUser().getId());
        smallProfileResponse.setUsername(userPostEntity.getUser().getUserProfile().getUsername());
        smallProfileResponse.setProfilePhotoUrl(userPostEntity.getUser().getUserProfile().getProfilePhotoUrl());

        userPostResponse.setId(userPostEntity.getId());
        userPostResponse.setPostDescription(userPostEntity.getPostDescription());
        userPostResponse.setCreateDate(userPostEntity.getCreateDate());
        userPostResponse.setProfile(smallProfileResponse);
        userPostResponse.setPostUrl(userPostEntity.getPostUrl());
        userPostResponse.setIsLiked(false);
        userPostResponse.setPostClass(userPostEntity.getPostClass());

        return userPostResponse;
    }

    public List<UserPostResponse> allToUserPostResponse(List<UserPostEntity> postEntityList){
        List<UserPostResponse> responseList = new ArrayList<>();
        for(UserPostEntity post: postEntityList){
            responseList.add(toUserPostResponse(post));
        }
        return responseList;
    }


}
