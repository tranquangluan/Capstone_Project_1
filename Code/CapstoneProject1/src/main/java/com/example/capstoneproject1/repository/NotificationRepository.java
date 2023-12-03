package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findNotificationByNotificationId(Long notificationId);

    @Query("SELECT n FROM Notification n " +
            "WHERE (n.receiver.id = :receiveId) " +
            "AND (:senderId IS NULL OR n.sender.id = :senderId) " )
    Page<Notification> findNotificationByConditions(
            @Param("receiveId") Integer receiveId,
            @Param("senderId") Integer senderId,
            Pageable pageable
    );
}
