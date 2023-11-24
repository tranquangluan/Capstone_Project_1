package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.request.BookingForm;
import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.booking.BookingResponse;
import com.example.capstoneproject1.dto.response.booking.ListBookingResponse;
import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.services.BookingService;
import com.example.capstoneproject1.services.UserService;
import com.example.capstoneproject1.services.ZaloPayService;
import com.example.capstoneproject1.services.space.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;
    @Autowired
    SpaceService spaceService;
    @Autowired
    private ZaloPayService zaloPayService;
    @GetMapping(value = "")
    public ResponseEntity<?> getAllBookings(@RequestParam(defaultValue = "0", required = false, name = "page") Integer page,
                                            @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
                                            @RequestParam(defaultValue = "title", required = false, name = "sortBy") String sortBy,
                                            @RequestParam(defaultValue = "ASC", required = false, name = "sortDir") String sortDir,
                                            @RequestParam(defaultValue = "1", required = false, name = "status") Integer status) {
        try {
            List<Booking> bookingList = bookingService.getAllBookings(status, page, limit, sortBy, sortDir);
            if (!bookingList.isEmpty())
                return new ResponseEntity<>(new ListBookingResponse(0, "Get Booking Successfully", bookingList.size(), bookingList, 200), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ListBookingResponse(1, "Booking Not Found", 0, 404), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public @ResponseBody ResponseEntity<?> createBooking(@Valid BookingForm bookingForm) {
        try {
            Optional<User> userOptional = userService.findById(bookingForm.getUserId());
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            Optional<Space> spaceOptional = spaceService.findById(bookingForm.getSpaceId());
            if (!spaceOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);

            Booking booking = new Booking(userOptional.get(),spaceOptional.get(),bookingForm.getTotalPrice(),bookingForm.getStatus(), Date.valueOf(String.valueOf(LocalDateTime.now())));
            bookingService.update(booking);
            return new ResponseEntity<>(new BookingResponse(0, "Create Booking Successful!", booking, 201), HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new BookingResponse(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteBooking(@RequestParam(name = "id") Integer id) {
        try {
            Booking booking = bookingService.findBookingById(id);
            if (booking.equals(null)){
                return new ResponseEntity<>(new BookingResponse(1, "Booking not found!", 201), HttpStatus.NOT_FOUND);
            } else
                bookingService.deleteBookingById(id);
                return new ResponseEntity<>(new BookingResponse(0, "Delete Booking Successfully!", booking, 201), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new BookingResponse(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
}
