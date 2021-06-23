package com.safestagram.ws.mapper;

import com.safestagram.ws.model.reponse.UserProfileResponse;
import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.persistence.entity.UserProfileEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfileEntity toUserProfileFromRegisterRequest(UserRegisterRequest userRegisterRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfileResponse toUserProfileResponse(UserProfileEntity userProfileEntity);
}
