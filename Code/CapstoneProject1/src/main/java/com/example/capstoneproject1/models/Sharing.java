package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Sharing {
    @Id
    @Column(name = "Sharing_Id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "Space_id")
    private Space spaceId;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User userId;
    @Column(name = "Status")
    private Boolean status;
}
