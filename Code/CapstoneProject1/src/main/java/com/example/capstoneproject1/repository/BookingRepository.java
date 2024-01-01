package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Booking;

import com.example.capstoneproject1.models.Status;

import com.example.capstoneproject1.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT b FROM Booking b " +
            "WHERE (:status IS NULL OR b.status.id = :status)" +
            "AND (:priceFrom IS NULL OR b.totalPrice >= :priceFrom) " +
            "AND (:priceTo IS NULL OR b.totalPrice <= :priceTo) " +
            "AND (:ownerId IS NULL OR b.userId.id = :ownerId)"
            )
    Page<Booking> getAllBooking(
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            @Param("status") Integer status,
            @Param("ownerId") Integer ownerId,
            Pageable pageable
    );
    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET " +
            "b.dateArrive = :dayArrive " +
            "WHERE b.id = :id")
    void updateDayArrive(
            @Param("id") Integer id,
            @Param("dayArrive")@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date dayArrive
    );
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Booking b WHERE b.booking_id = :id",nativeQuery = true)
    void deleteBookingById(Integer id);
    @Query(value = "SELECT * FROM Booking WHERE booking_id = :id", nativeQuery = true)
    Booking findBookingById(Integer id);

    Optional<Booking> findById(Integer integer);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.userId = :user AND b.spaceId.ownerId = :owner")
    Boolean existsBookingByUserAndOwner(@Param("user") User user, @Param("owner") User owner);




}
