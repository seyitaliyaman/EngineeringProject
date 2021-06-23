package com.safestagram.ws.persistence.repository;

import com.safestagram.ws.persistence.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    Optional<UserAccountEntity> findByUsername(String username);
    Optional<UserAccountEntity> findByUsernameAndPassword(String username, String password);

}
