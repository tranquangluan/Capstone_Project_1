package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class CategorySpace {
    @Id
    @Column(name = "categoryId")
    private Integer categoryId;
    @Column(name = "categoryName")
    private String categoryName;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
