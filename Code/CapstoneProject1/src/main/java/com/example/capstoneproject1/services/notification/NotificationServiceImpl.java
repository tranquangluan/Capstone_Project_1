package com.example.capstoneproject1.services.notification;

import com.example.capstoneproject1.dto.response.notification.PageNotification;
import com.example.capstoneproject1.models.Notification;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.NotificationRepository;
import com.example.capstoneproject1.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements  NotificationService {


    @Autowired
    StatusRepository statusRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public Boolean createMessage(User sender, User receiver, String type, String message) {
        // handel find by status code
        Integer statusCodeNotSeen = 6;
        Optional<Status> statusNotSeen = statusRepository.findById(statusCodeNotSeen);
        if(!statusNotSeen.isPresent())
            return false;
        Notification notification = new Notification(sender ,receiver, type, message, statusNotSeen.get());
        notificationRepository.save(notification);
        return true;
    }

    @Override
    public PageNotification getListNotifications(Integer pageNo, Integer pageSize, String sortBy, String sortDir,Integer sender, Integer receiver) {
        try {
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Notification> notificationPage = notificationRepository.findNotificationByConditions(receiver,sender,pageable);
                Integer totalPages = notificationPage.getTotalPages();
                List<Notification> notifications = notificationPage.getContent();
                return new PageNotification(totalPages, notifications);
            }else {
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Notification> notificationPage = notificationRepository.findNotificationByConditions(receiver,sender,pageable);
                Integer totalPages = notificationPage.getTotalPages();
                List<Notification> notifications = notificationPage.getContent();
                return new PageNotification(totalPages, notifications);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new PageNotification();
        }
    }

    @Override
    public Boolean updateNotification(Long notificationId, Status status) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if(notificationOptional.isPresent()) {
            notificationOptional.get().setStatus(status);
            notificationRepository.save(notificationOptional.get());
            return true;
        }
        return false;
    }


}
