package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity

public class Image {
    @Id
    @Column(name = "imageId")
    private Integer id;
    @Column(name = "image")
    private String image;

}
