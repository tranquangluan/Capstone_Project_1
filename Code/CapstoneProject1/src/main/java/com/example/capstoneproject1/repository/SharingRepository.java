package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Sharing;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface SharingRepository extends JpaRepository<Sharing, Integer> {

    Optional<Sharing> findById(Integer sharingId);

    Optional<Sharing> findBySpaceIdAndUserId(Space space, User user);
    Optional<Sharing> findByUserId(User user);
    Optional<Sharing> findBySpaceId(Space space);

    @Query("SELECT s FROM Sharing s " +
            "WHERE (:statusSharing IS NULL OR s.status.id = :statusSharing) " +
            "AND (:categoryId IS NULL OR s.spaceId.categoryId.id = :categoryId) " +
            "AND (:searchByProvince IS NULL OR s.spaceId.province = :searchByProvince) " +
            "AND (:searchByDistrict IS NULL OR s.spaceId.district = :searchByDistrict) " +
            "AND (:searchByWard IS NULL OR s.spaceId.ward = :searchByWard) " +
            "AND (:priceFrom IS NULL OR s.spaceId.price >= :priceFrom) " +
            "AND (:priceTo IS NULL OR s.spaceId.price <= :priceTo) " +
            "AND (:areaFrom IS NULL OR s.spaceId.area >= :areaFrom) " +
            "AND (:areaTo IS NULL OR s.spaceId.area <= :areaTo) " +
            "AND (:spaceId IS NULL OR s.spaceId.id = :spaceId)" +
            "AND (:ownerId IS NULL OR s.spaceId.ownerId.id = :ownerId)" +
            "AND (:sharingId IS NULL OR s.id = :sharingId)" +
            "AND (:userSharingId IS NULL OR s.userId.id = :userSharingId)")
    Page<Sharing> findSpacesByConditions(
            @Param("statusSharing") Integer status,
            @Param("categoryId") Integer categoryId,
            @Param("searchByProvince") String searchByProvince,
            @Param("searchByDistrict") String searchByDistrict,
            @Param("searchByWard") String searchByWard,
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            @Param("areaFrom") Float areaFrom,
            @Param("areaTo") Float areaTo,
            @Param("spaceId") Integer spaceId,
            @Param("sharingId") Integer sharingId,
            @Param("ownerId") Integer ownerId,
            @Param("userSharingId") Integer userSharingId,
            Pageable pageable
    );

}
