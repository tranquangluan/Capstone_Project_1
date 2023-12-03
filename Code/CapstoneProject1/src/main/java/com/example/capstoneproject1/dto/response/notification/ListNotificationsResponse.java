package com.example.capstoneproject1.dto.response.notification;

import com.example.capstoneproject1.models.Notification;

import java.util.List;

public class ListNotificationsResponse {
    private Integer error;
    private String message;
    private Integer notificationQuantity;
    private Integer totalPages;

    private List<Notification> listNotifications;

    private Integer status;

    public ListNotificationsResponse() {
    }

    public ListNotificationsResponse(Integer error, String message, Integer notificationQuantity, Integer totalPages, List<Notification> listNotifications, Integer status) {
        this.error = error;
        this.message = message;
        this.notificationQuantity = notificationQuantity;
        this.totalPages = totalPages;
        this.listNotifications = listNotifications;
        this.status = status;
    }

    public ListNotificationsResponse(Integer error, String message, Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNotificationQuantity() {
        return notificationQuantity;
    }

    public void setNotificationQuantity(Integer notificationQuantity) {
        this.notificationQuantity = notificationQuantity;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Notification> getListNotifications() {
        return listNotifications;
    }

    public void setListNotifications(List<Notification> listNotifications) {
        this.listNotifications = listNotifications;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
