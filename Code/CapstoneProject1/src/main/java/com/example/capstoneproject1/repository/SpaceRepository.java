package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {
    @Query(value= "Select address from space",nativeQuery = true)
    List<Space> getAddress();
    @Query(value= "Select * from space s join user u on s.user_id = u.userid join category_space cs on s.category_id=cs.category_id",nativeQuery = true)
    List<Space> getList();
    @Query(value= "Select * from Space where (price between :priceMin and :priceMax) and (area between :areaMin and :areaMax) and category_id = :categoryId and province like %:province and district like %:district and ward like %:ward and address like %:address",nativeQuery = true)
    List<Space> search(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    @Query(value= "Select * from Space where (price between :priceMin and :priceMax) and (area between :areaMin and :areaMax) and category_id = :categoryId and province like %:province and district like %:district and ward like %:ward and address like %:address order by price asc",nativeQuery = true)
    List<Space> sortAsc(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    @Query(value= "Select * from Space where (price between :priceMin and :priceMax) and (area between :areaMin and :areaMax) and category_id = :categoryId and province like %:province and district like %:district and ward like %:ward and address like %:address order by price desc",nativeQuery = true)
    List<Space> sortDesc(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address);
    @Query(value= "Select * from space s join user u on s.user_id = u.userid join category_space cs on s.category_id=cs.category_id where space_id = :id",nativeQuery = true)
    Space detailSpace(Integer id);


    Optional<Space> findById(Integer spaceId);
    Integer countByCategoryId_Id(Integer category);

    void deleteAllByOwnerId_Id(Integer userId);
}
