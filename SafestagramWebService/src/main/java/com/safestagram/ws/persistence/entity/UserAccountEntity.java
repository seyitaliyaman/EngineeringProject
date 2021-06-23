package com.safestagram.ws.persistence.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "u_user_account")
@Data
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}
