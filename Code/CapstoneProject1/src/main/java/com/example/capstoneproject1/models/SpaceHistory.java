package com.example.capstoneproject1.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SpaceHistory {
    @Id
    @GeneratedValue
    @Column(name = "spaceHistoryId")
    private Integer id;

}
