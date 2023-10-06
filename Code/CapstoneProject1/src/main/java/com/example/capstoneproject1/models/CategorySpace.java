package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class CategorySpace {
    @Id
    @Column(name = "Category_id")
    private Integer categoryId;
    @Column(name = "Category_name")
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
