package com.example.capstoneproject1.dto.response.notification;

import com.example.capstoneproject1.models.Notification;

import java.util.List;

public class PageNotification {
    private Integer totalPages;
    private List<Notification> listNotifications;

    public PageNotification() {
    }

    public PageNotification(Integer totalPages, List<Notification> listNotifications) {
        this.totalPages = totalPages;
        this.listNotifications = listNotifications;
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
}
