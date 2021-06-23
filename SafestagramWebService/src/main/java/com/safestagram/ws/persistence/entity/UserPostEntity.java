package com.safestagram.ws.persistence.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "u_user_post")
@Data
public class UserPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "post_description")
    private String postDescription;
    @Column(name = "post_path")
    private String postPath;
    @Column(name = "post_size")
    private Integer postSize;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "post_class")
    private String postClass;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "post_url")
    private String postUrl;
    @ManyToOne
    private UserEntity user;
}
