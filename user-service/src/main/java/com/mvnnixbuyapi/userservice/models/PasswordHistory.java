package com.mvnnixbuyapi.userservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "password_history")
@NoArgsConstructor
public class PasswordHistory implements Serializable {
    private static final long serialVersionUID = 1003000000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user_app", nullable = false)
    private Long idUserApp;

    @Column(length = 255, nullable = false)
    private String passwordStored;
    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_app", insertable=false, updatable=false)//insertable=false, updatable=false porque idUserApp esta siendo usado como el FK
    private UserApplication userApplicationRelated;

}
