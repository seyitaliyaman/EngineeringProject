package com.safestagram.ws.persistence.repository;

import com.safestagram.ws.persistence.entity.UserAccountEntity;
import com.safestagram.ws.persistence.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity,Long> {
    Optional<UserSessionEntity> findByUserAccountAndLogoutDateNull(UserAccountEntity userAccountEntity);

}
