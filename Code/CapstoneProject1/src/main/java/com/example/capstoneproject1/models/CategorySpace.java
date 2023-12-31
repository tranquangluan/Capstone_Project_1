package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class CategorySpace {
    @Id
    @Column(name = "categoryId", columnDefinition = "int")
    private Integer id;
    @Column(name = "categoryName", columnDefinition = "nvarchar(50)")
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
