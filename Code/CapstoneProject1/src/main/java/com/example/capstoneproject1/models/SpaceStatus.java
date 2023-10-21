package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class SpaceStatus {
    @Id
    @Column(name="statusId")
    private Integer id;
    @Column(name="status")
    private String status;
}
