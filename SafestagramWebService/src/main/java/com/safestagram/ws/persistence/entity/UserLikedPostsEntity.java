package com.safestagram.ws.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "u_user_likes")
@Data
public class UserLikedPostsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserPostEntity scoredPost;

}
