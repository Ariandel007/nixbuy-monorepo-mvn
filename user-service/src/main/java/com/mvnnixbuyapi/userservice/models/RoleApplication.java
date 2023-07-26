package com.mvnnixbuyapi.userservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "roles_applications")
public class RoleApplication implements Serializable {
    private static final long serialVersionUID = 1002000000L;

    @Id
    private Long id;

    @Column(length = 255)
    private String name;

    @ManyToMany(mappedBy = "rolApplicationList")
    List<UserApplication> userApplicationList;

}
