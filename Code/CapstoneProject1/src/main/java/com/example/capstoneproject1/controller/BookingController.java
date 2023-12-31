package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.booking.BookingResponse;
import com.example.capstoneproject1.dto.response.booking.ListBookingResponse;
import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.services.ZaloPayService;
import com.example.capstoneproject1.services.booking.BookingService;
import com.example.capstoneproject1.services.space.SpaceService;
import com.example.capstoneproject1.services.status.StatusService;
import com.example.capstoneproject1.services.user.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;
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
    StatusService statusService;
    @Autowired
    ZaloPayService zaloPayService;

    @GetMapping(value = "/list-bookings")
    public ResponseEntity<?> getAllBookings(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                            @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
                                            @RequestParam(defaultValue = "date", required = false, name = "sortBy") String sortBy,
                                            @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
                                            @RequestParam(required = false, name = "priceFrom") BigDecimal priceFrom,
                                            @RequestParam(required = false, name = "priceTo") BigDecimal priceTo,
                                            @RequestParam(required = false, name = "status") Integer status,
                                            @RequestParam(required = false, name = "ownerId") Integer ownerId) {
        try {
            Page<Booking> bookingList = bookingService.getAllBookings(page - 1, limit, sortBy, sortDir, priceFrom, priceTo, status, ownerId);
            if (!bookingList.isEmpty()) {
                return new ResponseEntity<>(new ListBookingResponse(0, "Get Booking Successfully", (int) bookingList.getTotalElements(), bookingList.getTotalPages(), bookingList.getContent(), 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ListBookingResponse(1, "Booking Not Found", 0, 404), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage(1, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestParam(name = "userId") Integer userId,
                                                           @RequestParam(name = "id") Integer id) throws JSONException, IOException {

        Map<String, Object> resultOrder = zaloPayService.createOrder(userId,id);
        return new ResponseEntity<>(resultOrder, HttpStatus.OK);
    }
    @PostMapping("/order-status")
    public Map<String, Object> getStatusOrder(@RequestParam(name = "appTransId") String appTransId) throws JSONException, URISyntaxException, IOException {
        return zaloPayService.statusOrder(appTransId);
    }
    @PostMapping("/create-booking")
    public ResponseEntity<?> createBooking(@RequestParam(name = "userId") Integer userId,
                                           @RequestParam(name = "id") Integer id,
                                           @RequestParam(name = "status") Integer status) {
        try {
            Optional<User> userOptional = userService.findById(userId);
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            Optional<Space> spaceOptional = spaceService.findById(id);
            if (!spaceOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);

            BigDecimal paid = BigDecimal.valueOf(0);
            Optional<Status> statusOptional = statusService.findById(status); // param status
            if (!statusOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "Status Not Found!", 404), HttpStatus.NOT_FOUND);
            } else if (statusOptional.get().getId() == 1) {
                paid = spaceOptional.get().getPrice();
            } else if (statusOptional.get().getId() == 6) {
                paid = BigDecimal.valueOf(0);
            }
            Booking booking = new Booking(userOptional.get(), spaceOptional.get(), spaceOptional.get().getPrice(), statusOptional.get(), paid);
            if (spaceOptional.get().getStatus().getId()==0){
                if (spaceService.updateStatus(spaceOptional.get().getId(), statusOptional.get()) == false || spaceService.updateOwnerId(spaceOptional.get().getId(), userOptional.get()) == false) {
                    return new ResponseEntity<>(new BookingResponse(1, "Create Booking Fail!", booking, 400), HttpStatus.BAD_REQUEST);
                }
                bookingService.saveBooking(booking);
                return new ResponseEntity<>(new BookingResponse(0, "Create Booking Successful!", booking, 201), HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(new BookingResponse(0, "Space has been booked!", booking, 400), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new BookingResponse(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-booking")
    public ResponseEntity<?> deleteBooking(@RequestParam(name = "id") Integer id) {
        try {
            Optional<Booking> bookingOptional = bookingService.findBookingById(id);
            Booking booking = bookingOptional.get();
            if (booking == null) {
                return new ResponseEntity<>(new BookingResponse(1, "Booking not found!", 404), HttpStatus.NOT_FOUND);
            } else if (booking.getPaid().compareTo(BigDecimal.ZERO) != 0) {
                return new ResponseEntity<>(new BookingResponse(1, "You cannot delete a Booking you have already paid for!!!", booking, 400), HttpStatus.BAD_REQUEST);
            } else {
//                Optional<Space> spaceOptional = spaceService.findById(booking.getSpaceId().getId());
                Optional<Status> statusOptional = statusService.findById(0);
                if (spaceService.updateStatus(booking.getSpaceId().getId(), statusOptional.get()) == false){
                    return new ResponseEntity<>(new BookingResponse(1, "Delete Booking Fail!, Don't return spacestatus", 400), HttpStatus.BAD_REQUEST);
                }else {
                    bookingService.deleteBookingById(id);
                    return new ResponseEntity<>(new BookingResponse(0, "Delete Booking Successfully!", 200), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
}