package com.safestagram.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacebookUserModel {

    private String id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String birthday;
    private String gender;
    private String email;
    private FacebookPicture picture;
}
