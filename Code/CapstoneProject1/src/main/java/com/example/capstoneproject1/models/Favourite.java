package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Favourite {
    @Id
    @Column(name = "favouriteId")
    private Integer id;
    @Column(name = "status")
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userid;
}
