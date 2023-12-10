package com.example.capstoneproject1.dto.response;

import com.example.capstoneproject1.models.Space;

import java.util.List;
import java.util.Map;

public class CountByRoleAndCountPostByStatus {
    private Integer error;
    private String message;
    private Map<String, Integer> countOverview;
    private Integer status;

    public CountByRoleAndCountPostByStatus(Integer error, String message, Map<String, Integer> countOverview, Integer status) {
        this.error = error;
        this.message = message;
        this.countOverview = countOverview;
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

    public Map<String, Integer> getCountOverview() {
        return countOverview;
    }

    public void setCountOverview(Map<String, Integer> countOverview) {
        this.countOverview = countOverview;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
