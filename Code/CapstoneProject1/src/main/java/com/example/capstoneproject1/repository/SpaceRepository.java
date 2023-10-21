package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Space;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SpaceRepository extends CrudRepository<Space, Integer> {
    @Query(value= "Select * from Space where price = :price and area= :area and category_id = :categoryId and province= :province and district= :district and ward = :ward and address= :address",nativeQuery = true)
    List<Space> search(BigDecimal price, float area, Integer categoryId, String province, String district, String ward, String address);
    @Query(value= "Select * from space s join user u on s.user_id = u.userid where space_id = id",nativeQuery = true)
    Space detailSpace(Integer id);
}
