package com.infy.pinterest.entity.user;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor 
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String password;

    private String roles = "USER";
    
 // ---- CHILD RELATIONS ----

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoginAttempt> loginAttempts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordResetToken> resetTokens = new ArrayList<>();
}
