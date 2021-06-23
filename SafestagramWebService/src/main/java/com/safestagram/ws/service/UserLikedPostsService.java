package com.safestagram.ws.service;

import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.persistence.entity.UserLikedPostsEntity;
import com.safestagram.ws.persistence.entity.UserPostEntity;
import com.safestagram.ws.persistence.repository.UserLikedPostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class UserLikedPostsService {

    private final UserLikedPostsRepository userLikedPostsRepository;

    public UserLikedPostsEntity addPost(UserPostEntity userPost){
        UserLikedPostsEntity userLikedPost = new UserLikedPostsEntity();
        userLikedPost.setScoredPost(userPost);
        return userLikedPostsRepository.save(userLikedPost);
    }

    public UserLikedPostsEntity getByPost(UserPostEntity userPostEntity){
        return userLikedPostsRepository.findByScoredPost(userPostEntity).orElseThrow(()-> new SafestagramServiceException(ErrorCode.POST_NOT_FOUND));
    }

    public void delete(UserLikedPostsEntity userLikedPostsEntity){
        userLikedPostsRepository.delete(userLikedPostsEntity);
    }



}
