package com.safestagram.ws.persistence.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "u_user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private Set<UserProfileEntity> followingUsers;
    @ManyToMany
    private Set<UserProfileEntity> followerUsers;
    @ManyToMany
    private Set<UserProfileEntity> blockedUsers;
    @ManyToMany
    private Set<UserLikedPostsEntity> likedPosts;
    @OneToOne(cascade = CascadeType.PERSIST)
    private UserProfileEntity userProfile;
    @OneToOne(cascade = CascadeType.PERSIST)
    private UserDetailsEntity userDetails;
    @OneToOne(cascade = CascadeType.PERSIST)
    private UserAccountEntity userAccount;
}
