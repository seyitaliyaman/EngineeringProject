package com.safestagram.ws.service;

import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.model.request.LoginRequest;
import com.safestagram.ws.model.request.UserRegisterRequest;
import com.safestagram.ws.persistence.entity.UserAccountEntity;
import com.safestagram.ws.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class AuthService {

    private final UserService userService;
    private final UserAccountService userAccountService;
    private final UserSessionService userSessionService;


    public void register(UserRegisterRequest userRegisterRequest){
        userService.create(userRegisterRequest);
    }

    public UserEntity login(LoginRequest loginRequest){
        UserEntity userEntity = userService.getByAccount(userAccountService.getByLoginRequest(loginRequest));
        if(!userEntity.getUserDetails().getIsDeleted()){
            userSessionService.loginSession(userEntity.getUserAccount());
            return userEntity;
        }else
            throw new SafestagramServiceException(ErrorCode.USER_DELETED);
    }

    public void logout(Long userId){
        userSessionService.logoutSession(userAccountService.getById(userId));
    }

    public void changePassword(Long userId, String password){
        UserAccountEntity userAccountEntity = userAccountService.getById(userId);
        userAccountEntity.setPassword(password);
        userAccountService.update(userAccountEntity);
    }

}
