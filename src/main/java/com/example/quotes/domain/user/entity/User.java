package com.example.quotes.domain.user.entity;

import com.example.quotes.common.entity.BaseEntity;
import com.example.quotes.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column
    private String profileUrl;

    @Column
    private String nickname;

    private User(String email, String password, UserRole userRole, String profileUrl, String nickname) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

    public static User create(String email, String password, UserRole userRole, String profileUrl, String nickname) {
        return new User(email, password, userRole, profileUrl, nickname);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUser(String profileUrl, String nickname) {
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

}
