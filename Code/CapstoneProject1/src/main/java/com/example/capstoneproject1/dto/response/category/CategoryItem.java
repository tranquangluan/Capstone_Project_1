package com.example.capstoneproject1.dto.response.category;

import com.example.capstoneproject1.models.CategorySpace;

public class CategoryItem {

    private CategorySpace categorySpace;
    private Integer categoryQuantity;

    public CategoryItem() {
    }

    public CategoryItem(CategorySpace categorySpace, Integer categoryQuantity) {
        this.categorySpace = categorySpace;
        this.categoryQuantity = categoryQuantity;
    }

    public CategorySpace getCategorySpace() {
        return categorySpace;
    }

    public void setCategorySpace(CategorySpace categorySpace) {
        this.categorySpace = categorySpace;
    }

    public Integer getCategoryQuantity() {
        return categoryQuantity;
    }

    public void setCategoryQuantity(Integer categoryQuantity) {
        this.categoryQuantity = categoryQuantity;
    }
}
