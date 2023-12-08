package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.feedback.FeedbackResponse;
import com.example.capstoneproject1.dto.response.feedback.ListFeedbackResponse;
import com.example.capstoneproject1.dto.response.feedback.PageFeedback;
import com.example.capstoneproject1.dto.response.space.ListSpaceResponse;
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
import java.util.List;
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
    public @ResponseBody ResponseEntity<?> createFeedback(@RequestParam(name = "receiverId") Integer receiverId,
                                                          @NotNull Integer rate,
                                                          @NotNull String comment,
                                                          HttpServletRequest request) {
        try {
            Optional<User> userOptional = getUserFromToken(request);
            // check existing user
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            //check existing receiver
            Optional<User> receiverOptional = userService.findById(receiverId);
            if (!receiverOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Owner Not Found!", 404), HttpStatus.NOT_FOUND);

            User sender = userOptional.get();
            User receiver = receiverOptional.get();
            //check existing booking of user with owner
            Boolean isExistingBooking = bookingService.existsBookingWithUserAndOwner(sender, receiver);
            if (!isExistingBooking)
                return new ResponseEntity<>(new ResponseMessage(1, "You cannot rate until you have booked with this owner!", 400), HttpStatus.BAD_REQUEST);

            // check existing feedback
            Boolean isExistingFeedbackWithOwner = feedbackService.existsFeedbackBySenderAnsReceiver(sender, receiver);
            if (isExistingFeedbackWithOwner)
                return new ResponseEntity<>(new ResponseMessage(1, "You have rated this owner!", 400), HttpStatus.BAD_REQUEST);

            Feedback feedback = new Feedback(sender, receiver, rate, comment);
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
    public @ResponseBody ResponseEntity<?> updateFeedback(@RequestParam(name = "receiverId") Integer receiverId,
                                                          @NotNull Integer rate,
                                                          @NotNull String comment,
                                                          HttpServletRequest request) {
        try {
            Optional<User> userOptional = getUserFromToken(request);
            // check existing user
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            //check existing receiver
            Optional<User> receiverOptional = userService.findById(receiverId);
            if (!receiverOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Owner Not Found!", 404), HttpStatus.NOT_FOUND);

            User sender = userOptional.get();
            User receiver = receiverOptional.get();
            // check feedback
            Optional<Feedback> feedbackOptional = feedbackService.findFeedbackBySenderAnsReceiver(sender,receiver);
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

    @PreAuthorize("hasAnyAuthority('User','Owner')")
    @DeleteMapping(value = "/delete-feedback")
    public ResponseEntity<?> deleteFeedback(@RequestParam(name = "feedbackId") Integer feedbackId,
                                                          HttpServletRequest request) {
        try {
            Optional<User> userOptional = getUserFromToken(request);
            // check existing user
            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            //check existing receiver
            Optional<Feedback> feedbackOptional = feedbackService.findById(feedbackId);
            if (!feedbackOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Feedback Not Found!", 404), HttpStatus.NOT_FOUND);

            Feedback feedback = feedbackOptional.get();
            feedbackService.deleteFeedback(feedback);
            return new ResponseEntity<>(new FeedbackResponse(0, "Delete feedback successful!", feedback, 200), HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, "Delete feedback fail!", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list-feedback")
    public ResponseEntity<?> getFeedback(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                         @RequestParam(defaultValue = "6", required = false, name = "limit") Integer limit,
                                         @RequestParam(defaultValue = "rate", required = false, name = "sortBy") String sortBy,
                                         @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
                                         @RequestParam( required = false, name = "rateFrom") Integer rateFrom,
                                         @RequestParam( required = false, name = "rateTo") Integer rateTo,
                                         @RequestParam( required = false, name = "ownerId") Integer ownerId,
                                         @RequestParam( required = false, name = "userId") Integer userId) {
        try {
            PageFeedback pageFeedback = feedbackService.getFeedback(page-1, limit, sortBy, sortDir, rateFrom, rateTo, ownerId, userId);
            Integer totalPages = pageFeedback.getTotalPages();
            List<Feedback> listFeedbacks = pageFeedback.getListFeedbacks();
            if (!listFeedbacks.isEmpty())
                return new ResponseEntity<>(new ListFeedbackResponse(0, "Get feedbacks successfully!", totalPages,listFeedbacks.size(), listFeedbacks, 200), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ListSpaceResponse(1, "Feedback Not Found", 0, 404), HttpStatus.NOT_FOUND);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, "Get feedbacks fail!", 400), HttpStatus.BAD_REQUEST);
        }
    }

}
