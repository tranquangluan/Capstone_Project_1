package com.example.capstoneproject1.dto.response.user;

import com.example.capstoneproject1.models.User;

import java.util.List;

public class ListUsersResponse {

    private Integer error;
    private String message;
    private Integer usersQuantity;
    private Integer totalPages;
    private List<User> listUsers;
    private Integer status;

    public ListUsersResponse() {
    }

    public ListUsersResponse(Integer error, String message, Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public ListUsersResponse(Integer error, String message, Integer usersQuantity, Integer totalPages, List<User> listUsers, Integer status) {
        this.error = error;
        this.message = message;
        this.usersQuantity = usersQuantity;
        this.totalPages = totalPages;
        this.listUsers = listUsers;
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

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUsersQuantity() {
        return usersQuantity;
    }

    public void setUsersQuantity(Integer usersQuantity) {
        this.usersQuantity = usersQuantity;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
