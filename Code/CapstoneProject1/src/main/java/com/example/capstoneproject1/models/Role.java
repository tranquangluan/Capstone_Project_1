package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleCode")
    private String roleCode;
    @Column(name = "roleValue")
    private String roleValue;
}
