package com.safestagram.ws.service;

import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.mapper.UserAccountMapper;
import com.safestagram.ws.model.request.LoginRequest;
import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.persistence.entity.UserAccountEntity;
import com.safestagram.ws.persistence.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    public UserAccountEntity createFromUserRegisterRequest(UserRegisterRequest userRegisterRequest){

        UserAccountEntity userAccountEntity = userAccountMapper.toUserAccountFromRegisterRequest(userRegisterRequest);
        return userAccountEntity;
    }

    public UserAccountEntity getByLoginRequest(LoginRequest loginRequest){
        return userAccountRepository.findByUsernameAndPassword(loginRequest.getUsername(),loginRequest.getPassword())
                .orElseThrow(()->new SafestagramServiceException(ErrorCode.LOGIN_REQUEST_INCORRECT));
    }

    public UserAccountEntity getById(Long id){
        return userAccountRepository.findById(id).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_ACCOUNT_NOT_FOUND));
    }

    public Boolean checkByUsername(String username){
        return userAccountRepository.findByUsername(username).isPresent();
    }

    public void updateByUsername(String username){
        UserAccountEntity userAccountEntity = userAccountRepository.findByUsername(username).orElseThrow(()-> new SafestagramServiceException(ErrorCode.USER_ACCOUNT_NOT_FOUND));
        userAccountRepository.save(userAccountEntity);
    }

    public void update(UserAccountEntity userAccountEntity){
        userAccountRepository.save(userAccountEntity);
    }

}
