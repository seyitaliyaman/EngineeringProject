package com.safestagram.ws.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String profileDescription;
    private int postCount;
    private int followersCount;
    private int followingCount;
    private Boolean isFollow;
    private String profilePhotoUrl;
}
