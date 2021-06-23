package com.safestagram.ws.persistence.repository;

import com.safestagram.ws.persistence.entity.UserProfileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends PagingAndSortingRepository<UserProfileEntity, Long> {

    Optional<List<UserProfileEntity>> findByUsernameContaining(String username);
}
