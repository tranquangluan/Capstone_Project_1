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

    @Query("SELECT DISTINCT s FROM Space s " +
            "LEFT JOIN Feedback f ON f.userReceiveFeedBack.id = s.ownerId.id " +
            "WHERE (:topRate IS NULL OR f IS NOT NULL AND f.rate >= :topRate) " +
            "AND (:status IS NULL OR s.status.id = :status) " +
            "AND (:categoryId IS NULL OR s.categoryId.id = :categoryId) " +
            "AND (:searchByProvince IS NULL OR s.province LIKE CONCAT('%', :searchByProvince, '%')) " +
            "AND (:searchByDistrict IS NULL OR s.district LIKE CONCAT('%', :searchByDistrict, '%')) " +
            "AND (:searchByWard IS NULL OR s.ward LIKE CONCAT('%', :searchByWard, '%')) " +
            "AND (:priceFrom IS NULL OR s.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR s.price <= :priceTo) " +
            "AND (:areaFrom IS NULL OR s.area >= :areaFrom) " +
            "AND (:areaTo IS NULL OR s.area <= :areaTo) " +
            "AND (:spaceId IS NULL OR s.id = :spaceId) " +  
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
            @Param("topRate") Integer topRate,
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

    @Query(value = "SELECT dates.date, COUNT(s.space_id) AS count " +
            "FROM (SELECT CURDATE() - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY AS date " +
            "      FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "            UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a " +
            "      CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "                  UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b " +
            "      CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "                  UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c " +
            "     ) AS dates " +
            "LEFT JOIN space s ON DATE(s.created_at) = dates.date AND s.status_id = 0 " +
            "WHERE dates.date >= DATE_SUB(CURDATE(), INTERVAL :date DAY) " +
            "GROUP BY dates.date " +
            "ORDER BY dates.date DESC", nativeQuery = true)
    List<Object[]> getStaticPostByDate(@Param("date") Integer date);

    @Query(value = "SELECT DATE(dates.date) AS date, COUNT(s.space_id) AS total " +
            "FROM " +
            "(SELECT DATE(DATE_SUB(CONCAT(:year, '-', :month, '-01'), INTERVAL 1 DAY) + INTERVAL a + b DAY) AS date " +
            "FROM " +
            "(SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a, " +
            "(SELECT 0 AS b UNION ALL SELECT 10 UNION ALL SELECT 20 UNION ALL SELECT 30) AS b) AS dates " +
            "LEFT JOIN space s ON DATE(s.created_at) = dates.date AND s.status_id = 0 " +
            "WHERE MONTH(dates.date) = :month AND YEAR(dates.date) = :year " +
            "GROUP BY dates.date " +
            "ORDER BY dates.date", nativeQuery = true)
    List<Object[]> getStaticPostByMonthAndYear(
            @Param("month") Integer month,
            @Param("year") Integer year);

    @Query(value = "SELECT months.month, COALESCE(totals.total, 0) AS total " +
            "FROM " +
            "(SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 " +
            "UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) AS months " +
            "LEFT JOIN " +
            "(SELECT EXTRACT(MONTH FROM created_at) AS month, COUNT(*) AS total " +
            "FROM space s " +
            "WHERE YEAR(created_at) = :year AND s.status_id = 0 " +
            "GROUP BY EXTRACT(MONTH FROM created_at)) AS totals " +
            "ON months.month = totals.month " +
            "ORDER BY months.month", nativeQuery = true)
    List<Object[]> getStaticPostByYear(@Param("year") Integer year);

    @Query(value = "SELECT dates.date, COUNT(s.space_id) AS count " +
            "FROM (SELECT CURDATE() - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY AS date " +
            "      FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "            UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a " +
            "      CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "                  UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b " +
            "      CROSS JOIN (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "                  UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS c " +
            "     ) AS dates " +
            "LEFT JOIN space s ON DATE(s.created_at) = dates.date AND s.status_id = 1 " +
            "WHERE dates.date >= DATE_SUB(CURDATE(), INTERVAL :date DAY) " +
            "GROUP BY dates.date " +
            "ORDER BY dates.date DESC", nativeQuery = true)
    List<Object[]> getStaticBookingByDate(@Param("date") Integer date);

    @Query(value = "SELECT DATE(dates.date) AS date, COUNT(s.space_id) AS total " +
            "FROM " +
            "(SELECT DATE(DATE_SUB(CONCAT(:year, '-', :month, '-01'), INTERVAL 1 DAY) + INTERVAL a + b DAY) AS date " +
            "FROM " +
            "(SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 " +
            "UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a, " +
            "(SELECT 0 AS b UNION ALL SELECT 10 UNION ALL SELECT 20 UNION ALL SELECT 30) AS b) AS dates " +
            "LEFT JOIN space s ON DATE(s.created_at) = dates.date AND s.status_id = 1 " +
            "WHERE MONTH(dates.date) = :month AND YEAR(dates.date) = :year " +
            "GROUP BY dates.date " +
            "ORDER BY dates.date", nativeQuery = true)
    List<Object[]> getStaticBookingByMonthAndYear(
            @Param("month") Integer month,
            @Param("year") Integer year);

    @Query(value = "SELECT months.month, COALESCE(totals.total, 0) AS total " +
            "FROM " +
            "(SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 " +
            "UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) AS months " +
            "LEFT JOIN " +
            "(SELECT EXTRACT(MONTH FROM created_at) AS month, COUNT(*) AS total " +
            "FROM space s " +
            "WHERE YEAR(created_at) = :year AND status_id = 1 " +
            "GROUP BY EXTRACT(MONTH FROM created_at)) AS totals " +
            "ON months.month = totals.month " +
            "ORDER BY months.month", nativeQuery = true)
    List<Object[]> getStaticBookingByYear(@Param("year") Integer year);
}