package com.safestagram.ws.persistence.repository;

import com.safestagram.ws.persistence.entity.UserEntity;
import com.safestagram.ws.persistence.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPostRepository extends JpaRepository<UserPostEntity, Long> {

    Optional<UserPostEntity> findByIdAndUserAndIsDeleted(Long postId, UserEntity userEntity, Boolean isDeleted);
    Optional<List<UserPostEntity>> findAllByUserOrderById(UserEntity userEntity);
    Optional<List<UserPostEntity>> findAllByUserAndIsDeletedOrderById(UserEntity userEntity, Boolean isDeleted);

    Optional<List<UserPostEntity>> findByUserNotOrderByCreateDateDesc(UserEntity userEntity);

    Integer countByUser(UserEntity userEntity);
}

