package com.example.capstoneproject1.controller;

import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.booking.BookingResponse;
import com.example.capstoneproject1.dto.response.booking.ListBookingResponse;
import com.example.capstoneproject1.models.Booking;
import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.ZaloPayService;
import com.example.capstoneproject1.services.booking.BookingService;
import com.example.capstoneproject1.services.space.SpaceService;
import com.example.capstoneproject1.services.status.StatusService;
import com.example.capstoneproject1.services.user.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    BookingService bookingService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    UserService userService;
    @Autowired
    SpaceService spaceService;
    @Autowired
    StatusService statusService;
    @Autowired
    ZaloPayService zaloPayService;

    @GetMapping(value = "/booking-histories")
    public ResponseEntity<?> getAllBookings(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                            @RequestParam(defaultValue = "8", required = false, name = "limit") Integer limit,
                                            @RequestParam(defaultValue = "date", required = false, name = "sortBy") String sortBy,
                                            @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
                                            @RequestParam(required = false, name = "priceFrom") BigDecimal priceFrom,
                                            @RequestParam(required = false, name = "priceTo") BigDecimal priceTo,
                                            @RequestParam(required = false, name = "status") Integer status,
                                            HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);
            Integer ownerId = userOptional.get().getId();
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
    public ResponseEntity<?> createOrder(@RequestParam(name = "id") Integer id,
                                         @RequestParam(name = "dayArrive") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date dayArrive,
                                         @RequestParam(name = "comment", required = false) String comment,
                                         HttpServletRequest request) throws JSONException, IOException {
        String token = jwtTokenFilter.getJwtFromRequest(request);
        String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
        Optional<User> userOptional = userService.findByEmail(userEmail);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
        }
        Optional<Space> spaceOptional = spaceService.findById(id);
        if (!spaceOptional.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);
        }
        if (spaceOptional.get().getOwnerId().getId() == userOptional.get().getId()) {
            return new ResponseEntity<>(new ResponseMessage(1, "You cannot book your own space!", 400), HttpStatus.BAD_REQUEST);
        }
        Date now = new Date();
        long currentTimeMillis = now.getTime(); // current date time
        long minimumBookingTimeMillis = currentTimeMillis + (5 * 60 * 60 * 1000); // current date time + 5 hours
        if (dayArrive.before(now)) {
            return new ResponseEntity<>(new ResponseMessage(1, "Your arrival time must be after the current time!", 400), HttpStatus.BAD_REQUEST);
        } else if (dayArrive.getTime() <= minimumBookingTimeMillis) {
            return new ResponseEntity<>(new ResponseMessage(1, "Your arrival time must be at least 5 hours away from the current time!", 400), HttpStatus.BAD_REQUEST);
        }
        Optional<Status> statusOptional = statusService.findById(8); // param status
        if (!statusOptional.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage(1, "Status Not Found!", 404), HttpStatus.NOT_FOUND);
        }
        if (spaceOptional.get().getStatus().getId() != 0) {
            return new ResponseEntity<>(new BookingResponse(1, "This space cannot be booked at this time!", 400), HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> resultOrder = zaloPayService.createOrder(userOptional.get().getId(), id);
        return new ResponseEntity<>(resultOrder, HttpStatus.OK);
    }

    @PostMapping("/create-booking")
    public ResponseEntity<?> getStatusOrder(@RequestParam(name = "appTransId") String appTransId,
//                                            @RequestParam(name = "userId") Integer userId,
                                            @RequestParam(name = "id") Integer id,
                                            @RequestParam(name = "dayArrive") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date dayArrive,
                                            @RequestParam(name = "comment", required = false) String comment,
                                            HttpServletRequest request) throws JSONException, URISyntaxException, IOException {
        try {
            Map<String, Object> result = zaloPayService.statusOrder(appTransId);
            if ((int) result.get("returncode") == 1 && result.get("returnmessage").toString().equals("Giao dịch thành công")) {
                String token = jwtTokenFilter.getJwtFromRequest(request);
                String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
                Optional<User> userOptional = userService.findByEmail(userEmail);
//                Optional<User> userOptional = userService.findById(userId);
                return createBooking(userOptional.get().getId(), id, dayArrive, comment, result);
            } else {
                return new ResponseEntity<>(new BookingResponse(0, "System Failed", result, 400), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> createBooking(Integer userId, Integer id, Date dayArrive, String comment, Map<String, Object> result) {
        try {
            Optional<User> userOptional = userService.findById(userId);
            Optional<Space> spaceOptional = spaceService.findById(id);
            Optional<Status> statusOptional = statusService.findById(8); // param status
            BigDecimal paid = spaceOptional.get().getPrice();
            Booking booking = new Booking(userOptional.get(), spaceOptional.get(), spaceOptional.get().getPrice(), statusOptional.get(), dayArrive, comment, paid);
            if (spaceService.updateStatus(spaceOptional.get().getId(), statusOptional.get()) == false || spaceService.updateOwnerId(spaceOptional.get().getId(), userOptional.get()) == false) {
                return new ResponseEntity<>(new BookingResponse(1, "Create Booking Fail!", booking, result, 400), HttpStatus.BAD_REQUEST);
            }
            bookingService.saveBooking(booking);
            return new ResponseEntity<>(new BookingResponse(0, "Create Booking Successful!", booking, result, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new BookingResponse(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-booking1")
    public ResponseEntity<?> createBooking1(@RequestParam(name = "id") Integer id,
                                            @RequestParam(name = "dayArrive") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date dayArrive,
                                            @RequestParam(name = "comment", required = false) String comment,
                                            HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);
            if (!userOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            }
            Optional<Space> spaceOptional = spaceService.findById(id);
            if (!spaceOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "Space Not Found!", 404), HttpStatus.NOT_FOUND);
            }
            if (spaceOptional.get().getOwnerId().getId() == userOptional.get().getId()) {
                return new ResponseEntity<>(new ResponseMessage(1, "You cannot book your own space!", 400), HttpStatus.BAD_REQUEST);
            }
            Date now = new Date();
            long currentTimeMillis = now.getTime(); // current date time
            long minimumBookingTimeMillis = currentTimeMillis + (3 * 60 * 60 * 1000); // current date time + 3 hours
            if (dayArrive.before(now)) {
                return new ResponseEntity<>(new ResponseMessage(1, "Your arrival time must be after the current time!", 400), HttpStatus.BAD_REQUEST);
            } else if (dayArrive.getTime() <= minimumBookingTimeMillis) {
                return new ResponseEntity<>(new ResponseMessage(1, "Your arrival time must be at least 3 hours away from the current time!", 400), HttpStatus.BAD_REQUEST);
            }
            Optional<Status> statusOptional = statusService.findById(7); // param status
            if (!statusOptional.isPresent()) {
                return new ResponseEntity<>(new ResponseMessage(1, "Status Not Found!", 404), HttpStatus.NOT_FOUND);
            }
            BigDecimal paid = spaceOptional.get().getPrice();
            Booking booking = new Booking(userOptional.get(), spaceOptional.get(), spaceOptional.get().getPrice(), statusOptional.get(), dayArrive, comment, paid);
            if (spaceOptional.get().getStatus().getId() == 0) {
                if (spaceService.updateStatus(spaceOptional.get().getId(), statusOptional.get()) == false || spaceService.updateOwnerId(spaceOptional.get().getId(), userOptional.get()) == false) {
                    return new ResponseEntity<>(new BookingResponse(1, "Create Booking Fail!", booking, 400), HttpStatus.BAD_REQUEST);
                }
                bookingService.saveBooking(booking);
                return new ResponseEntity<>(new BookingResponse(0, "Create Booking Successful!", booking, 201), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new BookingResponse(1, "This space cannot be booked at this time!", booking, 400), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new BookingResponse(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update-booking")
    public ResponseEntity<?> UpdateBookingDateArrive(@RequestParam(name = "id") Integer id,
                                                     @RequestParam(name = "dayArrive") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date dayArrive,
                                                     HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);
            Date now = new Date();
            long currentTimeMillis = now.getTime(); // current date time
            long minimumBookingTimeMillis = currentTimeMillis + (5 * 60 * 60 * 1000); // current date time + 5 hours
            if (dayArrive.before(now)) {
                return new ResponseEntity<>(new ResponseMessage(1, "Your arrival time must be after the current time!", 400), HttpStatus.BAD_REQUEST);
            } else if (dayArrive.getTime() <= minimumBookingTimeMillis) {
                return new ResponseEntity<>(new ResponseMessage(1, "Your arrival time must be at least 5 hours away from the current time!", 400), HttpStatus.BAD_REQUEST);
            }
            if (!userOptional.isPresent()) {
                return new ResponseEntity<>(new BookingResponse(1, "User not found!", 404), HttpStatus.NOT_FOUND);
            }
            Optional<Booking> bookingOptional = bookingService.findBookingById(id);
            if (!bookingOptional.isPresent()) {
                return new ResponseEntity<>(new BookingResponse(1, "Booking not found!", 404), HttpStatus.NOT_FOUND);
            } else {
                if (bookingService.updateBookingDateArrive(id, dayArrive) == false) {
                    return new ResponseEntity<>(new BookingResponse(1, "Update Booking Fail!", 400), HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>(new BookingResponse(0, "Update Booking Successfully!", 200), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(0, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
    }
    

}