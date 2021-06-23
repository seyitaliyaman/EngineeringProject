package com.safestagram.ws.service;

import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.mapper.UserDetailsMapper;
import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.persistence.entity.UserDetailsEntity;
import com.safestagram.ws.persistence.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsMapper userDetailsMapper;

    public UserDetailsEntity createFromUserRegisterRequest(UserRegisterRequest userRegisterRequest){
        UserDetailsEntity userDetailsEntity = userDetailsMapper.toUserDetailsFromRegisterRequest(userRegisterRequest);
        userDetailsEntity.setCreateDate(new Date());
        userDetailsEntity.setIsDeleted(false);
        return userDetailsEntity;
    }

    public Boolean checkByEmail(String email){
        return userDetailsRepository.findByEmail(email).isPresent();
    }

    public Optional<UserDetailsEntity> getByEmail(String email){
        return userDetailsRepository.findByEmail(email);
    }
}
