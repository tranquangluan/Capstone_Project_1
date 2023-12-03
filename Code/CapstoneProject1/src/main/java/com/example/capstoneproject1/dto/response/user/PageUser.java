package com.example.capstoneproject1.dto.response.user;

import com.example.capstoneproject1.models.User;

import java.util.List;

public class PageUser {

    private Integer totalPages;
    private List<User> listUsers;

    public PageUser() {
    }

    public PageUser(Integer totalPages, List<User> listUsers) {
        this.totalPages = totalPages;
        this.listUsers = listUsers;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }
}
