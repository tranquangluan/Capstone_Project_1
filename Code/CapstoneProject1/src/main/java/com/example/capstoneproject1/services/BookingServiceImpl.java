package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.repository.BookingRepository;
import com.example.capstoneproject1.repository.CategorySpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService{
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
    public void delete(Integer id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Iterable<Booking> findAll() {
        return (List<Booking>) bookingRepository.findAll();
    }
}
