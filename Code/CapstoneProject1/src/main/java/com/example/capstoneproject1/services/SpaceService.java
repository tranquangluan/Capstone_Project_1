package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.Space;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SpaceService {
    void update(Space space);
    Space findSpaceById(Integer id);
    void delete(Integer id);
    List<Space> search(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    List<Space> sortAsc(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    List<Space> sortDesc(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    Space detailSpace(Integer id);
    List<Space> getList();
    Iterable<Space> findAll();

    Integer countSpaceByCategoryId(Integer categoryId);

    Optional<Space> findById(Integer spaceId);
}
