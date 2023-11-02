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
    List<Space> search(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    List<Space> sortAsc(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    List<Space> sortDesc(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    Space detailSpace(Integer id);
    List<Space> getList();
    Iterable<Space> findAll();
}
