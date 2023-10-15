package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @Column(name = "Role_code")
    private Integer code;
    @Column(name = "Role_name")
    private Boolean name;
}
