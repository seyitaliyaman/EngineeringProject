package com.safestagram.ws.service;

import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.DatabaseException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.persistence.entity.UserAccountEntity;
import com.safestagram.ws.persistence.entity.UserSessionEntity;
import com.safestagram.ws.persistence.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = DatabaseException.class)
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;

    public void loginSession(UserAccountEntity userAccount){

        Optional<UserSessionEntity> session = userSessionRepository.findByUserAccountAndLogoutDateNull(userAccount);


        if(session.isPresent()){
            UserSessionEntity userSession = session.get();
            userSession.setLoginDate(new Date());
            userSession.setUserAccount(userAccount);
            userSessionRepository.save(userSession);
        }else{
            UserSessionEntity userSessionEntity = new UserSessionEntity();
            userSessionEntity.setLoginDate(new Date());
            userSessionEntity.setUserAccount(userAccount);
            userSessionRepository.save(userSessionEntity);
        }
    }

    public Long  logoutSession(UserAccountEntity userAccountEntity){
        UserSessionEntity userSessionEntity = userSessionRepository.findByUserAccountAndLogoutDateNull(userAccountEntity).orElseThrow(()->new SafestagramServiceException(ErrorCode.USER_SESSION_NOT_FOUN));
        userSessionEntity.setLogoutDate(new Date());
        return userSessionRepository.save(userSessionEntity).getId();
    }
}
