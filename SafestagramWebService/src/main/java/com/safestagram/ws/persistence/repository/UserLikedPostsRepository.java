package com.safestagram.ws.persistence.repository;

import com.safestagram.ws.persistence.entity.UserLikedPostsEntity;
import com.safestagram.ws.persistence.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikedPostsRepository extends JpaRepository<UserLikedPostsEntity, Long> {

    Optional<UserLikedPostsEntity> findByScoredPost(UserPostEntity userPostEntity);
}
