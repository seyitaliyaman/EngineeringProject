package com.safestagram.ws.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PostArchiveModel {

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private String postGuid;

    @Getter
    @Setter
    private String postType;

    @Getter
    @Setter
    private String postExtensionName;

    @Getter
    @Setter
    private byte[] postData;
}
