package com.example.capstoneproject1.services.notification;

import com.example.capstoneproject1.dto.response.notification.PageNotification;
import com.example.capstoneproject1.models.Notification;
import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.models.User;

import java.util.Optional;

public interface NotificationService {


    void deleteNotification(Long notificationId);

    Boolean createMessage(User sender, User receiver,String type,String message);

    PageNotification getListNotifications(Integer page, Integer limit, String sortBy, String sortDir, Integer sender, Integer receiver);

    Boolean updateNotification(Long notificationId, Status status);
    Optional<Notification> findById(Long notificationId);

}
