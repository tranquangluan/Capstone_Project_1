package com.example.capstoneproject1.controller;


import com.example.capstoneproject1.dto.response.ResponseMessage;
import com.example.capstoneproject1.dto.response.notification.ListNotificationsResponse;
import com.example.capstoneproject1.dto.response.notification.PageNotification;
import com.example.capstoneproject1.models.Notification;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.NotificationRepository;
import com.example.capstoneproject1.repository.StatusRepository;
import com.example.capstoneproject1.security.jwt.JwtTokenFilter;
import com.example.capstoneproject1.security.jwt.JwtTokenProvider;
import com.example.capstoneproject1.services.notification.NotificationServiceImpl;
import com.example.capstoneproject1.services.status.StatusServiceImpl;
import com.example.capstoneproject1.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    NotificationServiceImpl notificationService;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    StatusServiceImpl statusService;


    @Autowired
    StatusRepository statusRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @PreAuthorize("hasAnyAuthority('Admin','Owner')")
    @GetMapping(value = "/list-notifications")
    public ResponseEntity<?> getNotification(@RequestParam(defaultValue = "1", required = false, name = "page") Integer page,
                                       @RequestParam(defaultValue = "4", required = false, name = "limit") Integer limit,
                                       @RequestParam(defaultValue = "notificationId", required = false, name = "sortBy") String sortBy,
                                       @RequestParam(defaultValue = "None", required = false, name = "sortDir") String sortDir,
                                       @RequestParam(required = false, name = "senderId") Integer senderId,
                                       HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);

            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);
            Integer receiverId = userOptional.get().getId();

            PageNotification listNotificationPage = notificationService.getListNotifications(page - 1, limit, sortBy, sortDir, senderId, receiverId);
            List<Notification> notificationList = listNotificationPage.getListNotifications();

            if (!notificationList.isEmpty())
                return new ResponseEntity<>(new ListNotificationsResponse(0, "Get Notification Successfully", notificationList.size(), listNotificationPage.getTotalPages(), notificationList, 200), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ListNotificationsResponse(1, "Not Found Notification!", 404), HttpStatus.NOT_FOUND);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, "Get notification fail!", 400), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAnyAuthority('Admin','Owner')")
    @PutMapping(value = "/update-notification")
    public ResponseEntity<?> updateNotification(@RequestParam(name = "notificationId") Long notificationId,
                                       HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);

            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);

            // find default status pending
            Optional<Status> statusOptional = statusRepository.findById(7);
            if (!statusOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Status Not Found!", 404), HttpStatus.NOT_FOUND);



            Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
            if (!notificationOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Notification Not Found!", 404), HttpStatus.NOT_FOUND);

            notificationOptional.get().setStatus(statusOptional.get());

            Boolean isUpdated = notificationService.updateNotification(notificationId, statusOptional.get());

            if (isUpdated)
                return new ResponseEntity<>(new ResponseMessage(0, "Updated notification successfully", 200), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ResponseMessage(1, "Updated notification fail", 400), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, "Updated notification fail!", 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('Admin','Owner')")
    @DeleteMapping(value = "/delete-notification")
    public ResponseEntity<?> deleteNotification(@RequestParam(name = "notificationId") Long notificationId,
                                       HttpServletRequest request) {
        try {
            String token = jwtTokenFilter.getJwtFromRequest(request);
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            Optional<User> userOptional = userService.findByEmail(userEmail);

            if (!userOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "User Not Found!", 404), HttpStatus.NOT_FOUND);


            Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
            if (!notificationOptional.isPresent())
                return new ResponseEntity<>(new ResponseMessage(1, "Notification Not Found!", 404), HttpStatus.NOT_FOUND);

            // delete notification
            notificationService.deleteNotification(notificationId);
            return new ResponseEntity<>(new ResponseMessage(0, "Delete notification successfully", 200), HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ResponseMessage(1, "Delete notification fail!", 400), HttpStatus.BAD_REQUEST);
        }
    }


}
