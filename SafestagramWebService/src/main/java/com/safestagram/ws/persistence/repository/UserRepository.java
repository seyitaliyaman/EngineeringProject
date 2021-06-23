package com.safestagram.ws.persistence.repository;

import com.safestagram.ws.persistence.entity.UserAccountEntity;
import com.safestagram.ws.persistence.entity.UserDetailsEntity;
import com.safestagram.ws.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    Optional<UserEntity> findByUserAccount(UserAccountEntity userAccountEntity);
    @Query(nativeQuery = true, value = "SELECT COUNT(*) from u_user_post where user_id = :userId")
    Integer countPost(@Param("userId") Long userId);

    Optional<UserEntity> findByUserDetails(UserDetailsEntity userDetailsEntity);
}
