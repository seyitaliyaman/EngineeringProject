package com.safestagram.ws.persistence.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "u_user_profile")
@Data
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "profile_description")
    private String profileDescription;
    @Column(name = "profile_photo_path")
    private String profilePhotoPath;
    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birthdate")
    private Date birthdate;
}
