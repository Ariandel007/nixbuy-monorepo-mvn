package com.mvnnixbuyapi.userservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "roles_applications")
@AllArgsConstructor
@NoArgsConstructor
public class RoleApplication implements Serializable {
    private static final long serialVersionUID = 1002000000L;

    public RoleApplication(Long id, String name){
        this.id = id;
        this.name = name;
    }

    @Id
    private Long id;

    @Column(length = 255)
    private String name;

    @ManyToMany(mappedBy = "roleApplicationList")
    List<UserApplication> userApplicationList;

}
