package com.safestagram.ws.mapper;

import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.persistence.entity.UserAccountEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserAccountEntity toUserAccountFromRegisterRequest(UserRegisterRequest userRegisterRequest);
}
