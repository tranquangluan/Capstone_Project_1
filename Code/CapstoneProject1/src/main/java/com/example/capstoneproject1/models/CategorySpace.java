package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class CategorySpace {
    @Id
    @Column(name = "Category_id")
    private Integer categoryId;
    @Column(name = "Category_name")
    private String categoryName;
}
