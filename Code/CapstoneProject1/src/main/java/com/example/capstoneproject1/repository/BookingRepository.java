package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    Optional<Booking> findById(Integer integer);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.userId = :user AND b.spaceId.ownerId = :owner")
    Boolean existsBookingByUserAndOwner(@Param("user") User user, @Param("owner") User owner);

}
