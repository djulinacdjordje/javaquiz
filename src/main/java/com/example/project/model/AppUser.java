package com.example.project.model;

import com.example.project.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "app_user_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "app_user_username_unique", columnNames = "username")
        }
)
@Entity(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "last_accessed_at")
    private Timestamp lastAccessedAt;

    @Column(name = "high_score")
    private Double highScore;

    @Column(name = "all_answered")
    private Boolean allAnswered;

    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String username,
                   String password,
                   Role role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
