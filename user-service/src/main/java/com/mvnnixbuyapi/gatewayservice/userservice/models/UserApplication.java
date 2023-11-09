package com.mvnnixbuyapi.gatewayservice.userservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "user_applications")
public class UserApplication implements Serializable {
    private static final long serialVersionUID = 1001000000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, unique = true)
    private String username;

    @Column(length = 255)
    private String password;

    @Column(length = 255)
    private String email;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "is_blocked", nullable = false)
    private boolean blocked = false;

    @Column(name = "auth_type")
    private String authType = "email_registered";

    @Column(nullable = false)
    private short attemps = 0;

    @Column(length = 255, nullable = false)
    private String firstname;

    @Column(length = 255, nullable = false)
    private String lastname;

    @Column(length = 255, nullable = false)
    private String country;

    @Column(length = 255, nullable = false)
    private String city;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Column(name = "account_creation_date")
    private Instant accountCreationDate;

    @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @OneToMany(mappedBy = "userApplicationRelated", fetch = FetchType.LAZY)
    private List<PasswordHistory> passwordHistoryList;

    @ManyToMany
    @JoinTable(
            name = "users_roles_applications",
            joinColumns = @JoinColumn(name = "user_app_id"),
            inverseJoinColumns = @JoinColumn(name = "role_app_id"))
    List<RoleApplication> roleApplicationList;

}
