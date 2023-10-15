package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity

public class Image {
    @Id
    @Column(name = "Image_Id")
    private Integer id;
    @Column(name = "Image")
    private String image;

}
