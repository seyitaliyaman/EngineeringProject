package com.safestagram.ws.service;

import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.model.reponse.SmallProfileResponse;
import com.safestagram.ws.model.reponse.UserResponse;
import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.persistence.entity.UserAccountEntity;
import com.safestagram.ws.persistence.entity.UserDetailsEntity;
import com.safestagram.ws.persistence.entity.UserEntity;
import com.safestagram.ws.persistence.entity.UserProfileEntity;
import com.safestagram.ws.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final UserAccountService userAccountService;
    private final UserProfileService userProfileService;
    private final ArchiveService archiveService;


    public UserResponse get(Long id, Long userId){
        UserEntity user = userRepository.findById(id).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        UserEntity ownUser = userRepository.findById(userId).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        UserResponse userResponse = toUserResponse(userRepository.findById(id).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND)));
        if(ownUser.getFollowingUsers().contains(user.getUserProfile())){
            userResponse.setIsFollow(true);
        }else{
            userResponse.setIsFollow(false);
        }
        return userResponse;
    }

    public UserResponse get(Long id){
        return toUserResponse(userRepository.findById(id).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND)));
    }

    public List<SmallProfileResponse> getFollowers(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        List<SmallProfileResponse> followersList = new ArrayList<>();
        for(UserProfileEntity profile: user.getFollowerUsers()){
            SmallProfileResponse smallProfileResponse = userProfileService.toSmallProfileResponse(profile);
            if(user.getFollowingUsers().contains(profile)){
                smallProfileResponse.setIsFollowing(true);
            }else{
                smallProfileResponse.setIsFollowing(false);
            }
            followersList.add(smallProfileResponse);
        }
        return followersList;
    }

    public List<SmallProfileResponse> getFollowings(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        List<SmallProfileResponse> followingList = new ArrayList<>();
        for(UserProfileEntity profile: user.getFollowingUsers()){
            SmallProfileResponse smallProfileResponse = userProfileService.toSmallProfileResponse(profile);
            smallProfileResponse.setIsFollowing(true);
            followingList.add(smallProfileResponse);
        }
        return followingList;
    }

    public List<SmallProfileResponse> getBlockeds(Long id){
        UserEntity user = userRepository.findById(id).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        List<SmallProfileResponse> blockedList = new ArrayList<>();
        for(UserProfileEntity profile: user.getBlockedUsers()){
            SmallProfileResponse smallProfileResponse = userProfileService.toSmallProfileResponse(profile);
            smallProfileResponse.setIsFollowing(null);
            blockedList.add(smallProfileResponse);
        }
        return blockedList;
    }

    public UserEntity getById(Long id){
        return userRepository.findById(id).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getByUserDetails(UserDetailsEntity userDetailsEntity){
        return userRepository.findByUserDetails(userDetailsEntity).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
    }

    public UserProfileEntity createFacebookUser(UserEntity userEntity){
        if(userDetailsService.checkByEmail(userEntity.getUserDetails().getEmail()))
            throw new SafestagramServiceException(ErrorCode.EMAIL_EXIST);
        return userRepository.save(userEntity).getUserProfile();
    }

    public void create(UserRegisterRequest userRegisterRequest){

        if(userDetailsService.checkByEmail(userRegisterRequest.getEmail()))
            throw new SafestagramServiceException(ErrorCode.EMAIL_EXIST);
        if(userAccountService.checkByUsername(userRegisterRequest.getUsername()))
            throw new SafestagramServiceException(ErrorCode.USERNAME_EXIST);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserAccount(userAccountService.createFromUserRegisterRequest(userRegisterRequest));
        userEntity.setUserDetails(userDetailsService.createFromUserRegisterRequest(userRegisterRequest));
        userEntity.setUserProfile(userProfileService.createFromUserRegisterRequest(userRegisterRequest));

        userRepository.save(userEntity);
    }

    public Long updateUser(UserEntity userEntity){
        return userRepository.save(userEntity).getId();
    }

    public String  followUser(Long userId, Long followingUserId){
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        UserEntity followingUser = userRepository.findById(followingUserId).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        checkUserIsDeleted(followingUser);
        user.getFollowingUsers().add(followingUser.getUserProfile());
        followingUser.getFollowerUsers().add(user.getUserProfile());
        userRepository.save(followingUser);
        return userRepository.save(user).getUserDetails().getEmail();
    }

    public void unfollowUser(Long userId, Long unfollowedUserId){
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        UserEntity unfollowedUser = userRepository.findById(unfollowedUserId).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));

        checkUserIsDeleted(unfollowedUser);

        user.getFollowingUsers().remove(unfollowedUser.getUserProfile());
        unfollowedUser.getFollowerUsers().remove(user.getUserProfile());

        userRepository.save(user);
        userRepository.save(unfollowedUser);
    }

    public void blockUser(Long userId, Long blockedUserId){
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        UserEntity blockedUser = userRepository.findById(blockedUserId).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        checkUserIsDeleted(blockedUser);

        if(user.getFollowerUsers().contains(blockedUser.getUserProfile())){
            user.getFollowerUsers().remove(blockedUser.getUserProfile());
            blockedUser.getFollowingUsers().remove(user.getUserProfile());
        }
        if(user.getFollowingUsers().contains(blockedUser.getUserProfile())){
            user.getFollowingUsers().remove(blockedUser.getUserProfile());
            blockedUser.getFollowerUsers().remove(user.getUserProfile());
        }

        user.getBlockedUsers().add(blockedUser.getUserProfile());
        userRepository.save(user);
        userRepository.save(blockedUser);
    }

    public void unblockUser(Long userId, Long blockedUserId){
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
        UserEntity unblockedUser = userRepository.findById(blockedUserId).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));

        checkUserIsDeleted(unblockedUser);
        user.getBlockedUsers().remove(unblockedUser.getUserProfile());
        userRepository.save(user);
    }

    public void deleteUser(Long userId){
        UserEntity userEntity = getById(userId);
        userEntity.getUserDetails().setIsDeleted(true);
        userRepository.save(userEntity);
    }

    public UserEntity getByAccount(UserAccountEntity userAccountEntity) {
        return userRepository.findByUserAccount(userAccountEntity).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_NOT_FOUND));
    }

    public void checkUserIsDeleted(UserEntity userEntity){
        if(userEntity.getUserDetails().getIsDeleted()){
            throw new SafestagramServiceException(ErrorCode.USER_DELETED);
        }
    }

    public UserResponse toUserResponse(UserEntity userEntity){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setProfileDescription(userEntity.getUserProfile().getProfileDescription());
        userResponse.setUsername(userEntity.getUserAccount().getUsername());

        if(userEntity.getUserProfile().getProfilePhotoPath()!=null){
            userResponse.setProfilePhotoUrl(userEntity.getUserProfile().getProfilePhotoUrl());
        }
        userResponse.setFollowersCount(userEntity.getFollowerUsers().size());
        userResponse.setFollowingCount(userEntity.getFollowingUsers().size());
        userResponse.setPostCount(userRepository.countPost(userEntity.getId()));
        return userResponse;
    }

}