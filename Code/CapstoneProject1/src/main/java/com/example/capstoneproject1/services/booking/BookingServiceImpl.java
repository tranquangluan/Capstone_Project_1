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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void update(Booking booking) {
        bookingRepository.save(booking);
    }

//    @Override
//    public Booking findBookingById(Integer id) {
//        return bookingRepository.findBookingById(id);
//    }

    @Override
    public Optional<Booking> findBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public void deleteBookingById(Integer id) {
        bookingRepository.deleteBookingById(id);
    }

    @Override
    public Page<Booking> getAllBookings(Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        try {
            // Create Sorted instance
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            // create Pageable instance
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

            Page<Booking> pageBooking = bookingRepository.getAllBooking(status,pageable);

            return pageBooking;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public Boolean existsBookingWithUserAndOwner(User user, User owner) {
        return bookingRepository.existsBookingByUserAndOwner(user, owner);
    }
}
