package com.safestagram.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfilePhotoModel {

    private Long userId;
    private String profilePhotoGuid;
    private String profilePhotoExtensionName;
    private byte[] profilePhoto;
}
