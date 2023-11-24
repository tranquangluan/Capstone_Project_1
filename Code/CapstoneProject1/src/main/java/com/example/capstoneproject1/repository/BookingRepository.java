package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT * FROM Booking WHERE (:status IS NULL OR status = :status)",nativeQuery = true)
    Page<Booking> getAllBooking(
            @Param("status") Integer status,
            Pageable pageable
    );
    @Query(value = "DELETE FROM Booking WHERE booking_id = :id",nativeQuery = true)
    List<Booking> deleteBookingById(Integer id);
    @Query(value = "SELECT * FROM Booking WHERE booking_id = :id", nativeQuery = true)
    Booking findBookingById(Integer id);
}
