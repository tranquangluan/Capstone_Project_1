package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingById(Integer id) {
        bookingRepository.deleteBookingById(id);
    }

    @Override
    public Page<Booking> getAllBookings(Integer pageNo, Integer pageSize, String sortBy, String sortDir, BigDecimal priceFrom, BigDecimal priceTo, Integer status, Integer ownerId) {
        try {
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Booking> bookingPage = bookingRepository.getAllBooking(priceFrom, priceTo, status, ownerId, pageable);
                return bookingPage;
            } else {
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Booking> bookingPage = bookingRepository.getAllBooking(priceFrom, priceTo, status, ownerId, pageable);
                return bookingPage;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Page.empty();
        }
    }



    @Override
    public Boolean updateBookingDateArrive(Integer id, Date dayArrive) {
        try {
            bookingRepository.updateDayArrive(id, dayArrive);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi Đây: >>>>" + e.getMessage());
            return false;
        }
    }

    public Booking findById(Integer id) {
        return null;
    }

    @Override
    public Optional<Booking> findBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public void delete(Integer id) {
        bookingRepository.deleteBookingById(id);
    }



    @Override
    public Boolean existsBookingWithUserAndOwner(User user, User owner) {
        return bookingRepository.existsBookingByUserAndOwner(user, owner);
    }
}
