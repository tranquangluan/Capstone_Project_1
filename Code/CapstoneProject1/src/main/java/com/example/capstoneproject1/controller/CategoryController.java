package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.response.category.CategoriesResponse;
import com.example.capstoneproject1.dto.response.category.CategoryItem;
import com.example.capstoneproject1.dto.response.category.CategoryMessage;
import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.services.category.CategoryService;
import com.example.capstoneproject1.services.space.SpaceService;
import com.example.capstoneproject1.services.category.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    SpaceService spaceService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(@RequestParam(required = false, name = "categoryId") Integer categoryId) {
        try {
            if (categoryId != null) {
                if (categoryService.existsCategory(categoryId)) {
                    // handle get categories
                    CategorySpace categorySpace = categoryService.getCategoryById(categoryId);
                    Integer categoryQuantity = spaceService.countSpaceByCategoryId(categoryId);
                    return new ResponseEntity<>(new CategoryMessage(0, "Get Category Successful!", new CategoryItem(categorySpace, categoryQuantity), 200), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new CategoryMessage(1, "Not Found Category!", new CategoryItem(), 404), HttpStatus.NOT_FOUND);
                }
            }
            // get category items and add quantity
            List<CategorySpace> listCategories = categoryService.findAllCategory();
            List<CategoryItem> listCategoryWithQuantity = new ArrayList<>();
            for (CategorySpace categorySpace : listCategories) {
                Integer quantity = spaceService.countSpaceByCategoryId(categorySpace.getId());
                CategoryItem categoryItem = new CategoryItem(categorySpace, quantity);
                listCategoryWithQuantity.add(categoryItem);
            }
            return new ResponseEntity<>(new CategoriesResponse(0, "Get Categories Successful!", listCategoryWithQuantity, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CategoriesResponse(1, "Get Categories Fail!", new ArrayList<>(), 400), HttpStatus.BAD_REQUEST);
        }
    }


}
