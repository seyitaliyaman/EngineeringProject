package com.safestagram.ws.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmallProfileResponse {

    private Long id;
    private String username;
    private Boolean isFollowing;
    private String profilePhotoUrl;
}
