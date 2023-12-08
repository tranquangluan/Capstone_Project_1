package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.feedback.FeedbackResponse;
import com.example.capstoneproject1.models.Feedback;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.booking.BookingServiceImpl;
import com.example.capstoneproject1.services.feedback.FeedbackServiceImpl;
import com.example.capstoneproject1.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    BookingServiceImpl bookingService;

    @Autowired
    FeedbackServiceImpl feedbackService;

    public Optional<User> getUserFromToken(HttpServletRequest request) {
        String token = jwtTokenFilter.getJwtFromRequest(request);
        String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
        return userService.findByEmail(userEmail);
    }

    @PreAuthorize("hasAnyAuthority('User','Owner')")
    @PostMapping(value = "/create-feedback", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> createFeedback(@RequestParam(name = "ownerId") Integer ownerId,
                                                          @NotNull Integer rate,
                                                          @NotNull String comment,
                                                          HttpServletRequest request) {
        try {
            Optional<User> userOptional = getUserFromToken(request);
            // check existing user
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            //check existing receiver
            Optional<User> receiverOptional = userService.findById(ownerId);
            if (!receiverOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Owner Not Found!", 404), HttpStatus.NOT_FOUND);

            User sender = userOptional.get();
            User owner = receiverOptional.get();
            //check existing booking of user with owner
            Boolean isExistingBooking = bookingService.existsBookingWithUserAndOwner(sender, owner);
            if (!isExistingBooking)
                return new ResponseEntity<>(new ResponseMessage(1, "You cannot rate until you have booked with this owner!", 400), HttpStatus.BAD_REQUEST);

            // check existing feedback
            Boolean isExistingFeedbackWithOwner = feedbackService.existsFeedbackBySenderAnsReceiver(sender, owner);
            if (isExistingFeedbackWithOwner)
                return new ResponseEntity<>(new ResponseMessage(1, "You have rated this owner!", 400), HttpStatus.BAD_REQUEST);

            Feedback feedback = new Feedback(sender, owner, rate, comment);
            feedbackService.saveFeedback(feedback);
            return new ResponseEntity<>(new FeedbackResponse(0, "Create feedback successful!", feedback, 201), HttpStatus.CREATED);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, "Create feedback fail!", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('User','Owner')")
    @PutMapping(value = "/update-feedback", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public @ResponseBody ResponseEntity<?> updateFeedback(@RequestParam(name = "ownerId") Integer ownerId,
                                                          @NotNull Integer rate,
                                                          @NotNull String comment,
                                                          HttpServletRequest request) {
        try {
            Optional<User> userOptional = getUserFromToken(request);
            // check existing user
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            //check existing receiver
            Optional<User> receiverOptional = userService.findById(ownerId);
            if (!receiverOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Owner Not Found!", 404), HttpStatus.NOT_FOUND);

            User sender = userOptional.get();
            User owner = receiverOptional.get();
            // check feedback
            Optional<Feedback> feedbackOptional = feedbackService.findFeedbackBySenderAnsReceiver(sender,owner);
            if (!feedbackOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Feedback Not Found!", 404), HttpStatus.NOT_FOUND);

            Feedback feedback = feedbackOptional.get();
            feedback.setRate(rate);
            feedback.setComment(comment);
            feedbackService.saveFeedback(feedback);
            return new ResponseEntity<>(new FeedbackResponse(0, "Update feedback successful!", feedback, 200), HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, "Update feedback fail!", 400), HttpStatus.BAD_REQUEST);
        }
    }

}
