package com.example.capstoneproject1.services.booking;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Booking findById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Booking> findBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public void delete(Integer id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Iterable<Booking> findAll() {
        return (List<Booking>) bookingRepository.findAll();
    }

    @Override
    public Boolean existsBookingWithUserAndOwner(User user, User owner) {
        return bookingRepository.existsBookingByUserAndOwner(user, owner);
    }
}
