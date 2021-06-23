package com.safestagram.ws.service;

import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.mapper.UserProfileMapper;
import com.safestagram.ws.model.reponse.SmallProfileResponse;
import com.safestagram.ws.model.reponse.UserProfileResponse;
import com.safestagram.ws.model.request.UpdateProfileRequest;
import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.persistence.entity.UserProfileEntity;
import com.safestagram.ws.persistence.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final ArchiveService archiveService;

    public UserProfileResponse getProfileResponseById(Long id){
        UserProfileEntity userProfileEntity = userProfileRepository.findById(id).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_PROFILE_NOT_FOUND));
        UserProfileResponse userProfile = userProfileMapper.toUserProfileResponse(userProfileEntity);
        if(userProfileEntity.getProfilePhotoPath() != null){
            userProfile.setProfilePhotoUrl(userProfileEntity.getProfilePhotoUrl());
        }
        return userProfile;
    }

    public UserProfileEntity getProfileById(Long id){
        return userProfileRepository.findById(id).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_PROFILE_NOT_FOUND));
    }

    public void updateProfileDescription(Long id, UpdateProfileRequest updateProfileRequest){
        UserProfileEntity userProfileEntity = userProfileRepository.findById(id).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_PROFILE_NOT_FOUND));
        userProfileEntity.setProfileDescription(updateProfileRequest.getProfileDescription());
        userProfileEntity.setUsername(updateProfileRequest.getUsername());
        userProfileRepository.save(userProfileEntity);
    }

    public void uploadProfilePhoto(Long id, MultipartFile photo){
        UserProfileEntity userProfileEntity = userProfileRepository.findById(id).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_PROFILE_NOT_FOUND));
        if(archiveService.storeProfilePhoto(photo)){
            userProfileEntity.setProfilePhotoPath(archiveService.getCurrrentArchiveFileName());
        }
        String fileDownloadUri = "http://localhost:8080/api/v1/post/"+photo.getOriginalFilename();
        userProfileEntity.setProfilePhotoUrl(fileDownloadUri);
        userProfileRepository.save(userProfileEntity);
    }

    public void update(UserProfileEntity userProfileEntity){
        UserProfileEntity updateProfile = userProfileRepository.findById(userProfileEntity.getId()).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_PROFILE_NOT_FOUND));
        updateProfile.setProfilePhotoPath(userProfileEntity.getProfilePhotoPath());
        userProfileRepository.save(updateProfile);
    }

    public UserProfileEntity createFromUserRegisterRequest(UserRegisterRequest userRegisterRequest){
        UserProfileEntity userProfileEntity = userProfileMapper.toUserProfileFromRegisterRequest(userRegisterRequest);
        userProfileEntity.setUsername(userRegisterRequest.getUsername());
        return userProfileEntity;
    }

    public List<SmallProfileResponse> search(String name){
        List<UserProfileEntity> profileList = userProfileRepository.findByUsernameContaining(name).orElseGet(ArrayList::new);
        List<SmallProfileResponse> profileResponses = new ArrayList<>();
        for(UserProfileEntity profile: profileList){
            profileResponses.add(toSmallProfileResponse(profile));
        }
        return profileResponses;
    }

    public SmallProfileResponse toSmallProfileResponse(UserProfileEntity userProfileEntity){
        SmallProfileResponse smallProfileResponse = new SmallProfileResponse();
        smallProfileResponse.setId(userProfileEntity.getId());
        smallProfileResponse.setProfilePhotoUrl(userProfileEntity.getProfilePhotoUrl());
        smallProfileResponse.setUsername(userProfileEntity.getUsername());
        return smallProfileResponse;
    }
}
