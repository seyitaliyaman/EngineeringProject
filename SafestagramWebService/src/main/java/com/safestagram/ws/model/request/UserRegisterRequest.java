package com.safestagram.ws.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String firstName;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String gender;
    private Date birthdate;
}
