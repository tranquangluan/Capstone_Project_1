package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;

import java.math.BigDecimal;
import java.util.List;

public interface SpaceService {
    void update(Space space);
    Space findById(Integer id);
    void delete(Integer id);
    List<Space> search(BigDecimal price, float area, Integer categoryId,String province, String district, String ward, String address);
    Space detailSpace(Integer id);
    Iterable<Space> findAll();
}
