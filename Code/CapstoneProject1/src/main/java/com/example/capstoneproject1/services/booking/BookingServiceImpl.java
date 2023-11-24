package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.repository.BookingRepository;
import com.example.capstoneproject1.services.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void update(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public Booking findBookingById(Integer id) {
        return bookingRepository.findBookingById(id);
    }

    @Override
    public void deleteBookingById(Integer id) {
        bookingRepository.deleteBookingById(id);
    }

    @Override
    public List<Booking> getAllBookings(Integer status, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        try {
            // Create Sorted instance
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            // create Pageable instance
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

            Page<Booking> pageBooking = bookingRepository.getAllBooking(status,pageable);

            return pageBooking.getContent();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
