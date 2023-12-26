package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.CategorySpace;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {

    Optional<Space> findById(Integer spaceId);

    Integer countByCategoryId_Id(Integer category);

    void deleteAllByOwnerId_Id(Integer userId);

    @Query("SELECT s FROM Space s " +
            "WHERE (:status IS NULL OR s.status.id = :status) " +
            "AND (:categoryId IS NULL OR s.categoryId.id = :categoryId) " +
            "AND (:searchByProvince IS NULL OR s.province = :searchByProvince) " +
            "AND (:searchByDistrict IS NULL OR s.district = :searchByDistrict) " +
            "AND (:searchByWard IS NULL OR s.ward = :searchByWard) " +
            "AND (:priceFrom IS NULL OR s.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR s.price <= :priceTo) " +
            "AND (:areaFrom IS NULL OR s.area >= :areaFrom) " +
            "AND (:areaTo IS NULL OR s.area <= :areaTo) " +
            "AND (:spaceId IS NULL OR s.id = :spaceId)" +
            "AND (:ownerId IS NULL OR s.ownerId.id = :ownerId)")
    Page<Space> findSpacesByConditions(
            @Param("status") Integer status,
            @Param("categoryId") Integer categoryId,
            @Param("searchByProvince") String searchByProvince,
            @Param("searchByDistrict") String searchByDistrict,
            @Param("searchByWard") String searchByWard,
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            @Param("areaFrom") Float areaFrom,
            @Param("areaTo") Float areaTo,
            @Param("spaceId") Integer spaceId,
            @Param("ownerId") Integer ownerId,
            Pageable pageable
    );

    @Transactional
    @Modifying
    @Query("UPDATE Space s SET " +
            "s.title = COALESCE(:title, s.title), " +
            "s.price = COALESCE(:price, s.price), " +
            "s.description = COALESCE(:description, s.description), " +
            "s.bathroomNumbers = COALESCE(:bathroomNumbers, s.bathroomNumbers), " +
            "s.bedroomNumbers = COALESCE(:bedroomNumbers, s.bedroomNumbers), " +
            "s.peopleNumbers = COALESCE(:peopleNumbers, s.peopleNumbers), " +
            "s.area = COALESCE(:area, s.area), " +
            "s.province = COALESCE(:province, s.province), " +
            "s.district = COALESCE(:district, s.district), " +
            "s.ward = COALESCE(:ward, s.ward), " +
            "s.address = COALESCE(:address, s.address), " +
            "s.categoryId = COALESCE(:categoryId, s.categoryId) " +
            "WHERE s.id = :spaceId")
    void updateSpace(
            @Param("title") String title,
            @Param("price") BigDecimal price,
            @Param("description") String description,
            @Param("bathroomNumbers") Integer bathroomNumbers,
            @Param("bedroomNumbers") Integer bedroomNumbers,
            @Param("peopleNumbers") Integer peopleNumbers,
            @Param("area") Float area,
            @Param("province") String province,
            @Param("district") String district,
            @Param("ward") String ward,
            @Param("address") String address,
            @Param("categoryId") CategorySpace categorySpace,
            @Param("spaceId") Integer spaceId
    );

    @Transactional
    @Modifying
    @Query("UPDATE Space s SET " +
            "s.status = :statusId " +
            "WHERE s.id = :spaceId")
    void updateStatus(
            @Param("spaceId") Integer spaceId,
            @Param("statusId") Status status
    );
    @Transactional
    @Modifying
    @Query("UPDATE Space s SET " +
            "s.ownerId = :ownerId " +
            "WHERE s.id = :spaceId")
    void updateOwnerId(
            @Param("spaceId") Integer spaceId,
            @Param("ownerId") User ownerId
    );

    Optional<Space> findSpaceByIdAndOwnerId(Integer id, User owner);

    @Query(value = "SELECT COUNT(*) FROM space WHERE status_id=0", nativeQuery = true)
    Integer countSpaceByStatus0();

    @Query(value = "SELECT COUNT(*) FROM space WHERE status_id=1", nativeQuery = true)
    Integer countSpaceByStatus1();

    @Query(value = "SELECT COUNT(*) FROM space WHERE status_id=2", nativeQuery = true)
    Integer countSpaceByStatus2();

    @Query(value = "SELECT COUNT(*) FROM space WHERE status_id=3", nativeQuery = true)
    Integer countSpaceByStatus3();

    @Query(value = "SELECT COUNT(*) FROM space WHERE status_id=4", nativeQuery = true)
    Integer countSpaceByStatus4();

    @Query(value = "SELECT COUNT(*) FROM space WHERE status_id=5", nativeQuery = true)
    Integer countSpaceByStatus5();

    @Query("SELECT s FROM Space s " +
            "WHERE s.status.id = 0 " +
            "AND (:categoryId IS NULL OR s.categoryId.id = :categoryId) " +
            "AND (:searchByProvince IS NULL OR s.province = :searchByProvince) " +
            "AND (:searchByDistrict IS NULL OR s.district = :searchByDistrict) " +
            "AND (:searchByWard IS NULL OR s.ward = :searchByWard) " +
            "AND (:ownerId IS NULL OR s.ownerId.id = :ownerId)")
    Page<Space> getPostSpaceByConditions(
            @Param("categoryId") Integer categoryId,
            @Param("searchByProvince") String searchByProvince,
            @Param("searchByDistrict") String searchByDistrict,
            @Param("searchByWard") String searchByWard,
            @Param("ownerId") Integer ownerId,
            Pageable pageable
    );

    @Query(value = "SELECT DATE(created_at) AS date, COUNT(*) AS count\n" +
            "FROM space\n" +
            "WHERE status_id = 0 and created_at >= DATE_SUB(CURDATE(), INTERVAL :date DAY)\n" +
            "GROUP BY DATE(created_at)\n" +
            "ORDER BY DATE(created_at) DESC;", nativeQuery = true)
    List<Object[]> getStaticDashboardByDate(@Param("date") Integer date);
    @Query(value = "SELECT DATE(created_at) AS date,COUNT(*) AS total\n" +
            "FROM space\n" +
            "WHERE status_id = 0 and month(created_at) =:month AND year(created_at) =:year\n" +
            "GROUP BY DATE(created_at);;", nativeQuery = true)
    List<Object[]> getStaticDashboardByMonthAndYear(
            @Param("month") Integer month,
            @Param("year") Integer year);
    @Query(value = "SELECT EXTRACT(MONTH FROM created_at) AS month,COUNT(*) AS total\n" +
            "FROM space\n" +
            "WHERE status_id = 0 and YEAR(created_at) = :year " +
            "GROUP BY EXTRACT(MONTH FROM created_at);", nativeQuery = true)
    List<Object[]> getStaticDashboardByYear(@Param("year") Integer year);




}