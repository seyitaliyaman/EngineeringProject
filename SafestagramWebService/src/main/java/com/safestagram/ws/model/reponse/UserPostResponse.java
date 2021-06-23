package com.safestagram.ws.model.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostResponse {

    private Long id;
    private String postDescription;
    private Boolean isLiked;
    private Date createDate;
    private SmallProfileResponse profile;
    private String postUrl;
    private String postClass;
}
