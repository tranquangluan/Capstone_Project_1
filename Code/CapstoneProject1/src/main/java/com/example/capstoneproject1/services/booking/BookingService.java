package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Optional;

public interface BookingService {
    void saveBooking(Booking booking);
    void deleteBookingById(Integer id);
    Page<Booking> getAllBookings(Integer pageNo, Integer pageSize, String sortBy, String sortDir, BigDecimal priceFrom, BigDecimal priceTo, Integer status, Integer ownerId);



    Booking findById(Integer id);
    Optional<Booking> findBookingById(Integer bookingId);
    void delete(Integer id);

    Boolean existsBookingWithUserAndOwner(User user, User owner);
}
