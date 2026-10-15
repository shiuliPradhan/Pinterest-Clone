package com.infy.pinterest.entity.user;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String displayName;
    private String bio;
    private String profileImageUrl;
    private String phoneNumber;
    private String websiteUrl;
    private String location;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false, unique=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
