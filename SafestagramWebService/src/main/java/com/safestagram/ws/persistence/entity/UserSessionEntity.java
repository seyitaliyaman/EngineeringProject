package com.safestagram.ws.persistence.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "u_user_session")
@Data
public class UserSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login_date")
    private Date loginDate;
    @Column(name = "logout_date")
    private Date logoutDate;
    @ManyToOne
    private UserAccountEntity userAccount;
}
