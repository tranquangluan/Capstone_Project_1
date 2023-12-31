package com.example.capstoneproject1.dto.response.dashboard;

import java.util.Map;

public class StaticResponse {
    private Integer error;
    private String message;

    private Map<String,Integer> mapStaticPost;
    private Map<String,Integer> mapStaticBooking;
    private Integer status;

    public StaticResponse(Integer error, String message, Map<String, Integer> mapStaticPost, Map<String, Integer> mapStaticBooking, Integer status) {
        this.error = error;
        this.message = message;
        this.mapStaticPost = mapStaticPost;
        this.mapStaticBooking = mapStaticBooking;
        this.status = status;
    }

    public StaticResponse(Integer error, String message, Map<String, Integer> mapStaticPost, Integer status) {
        this.error = error;
        this.message = message;
        this.mapStaticPost = mapStaticPost;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, Integer> getMapStaticPost() {
        return mapStaticPost;
    }

    public void setMapStaticPost(Map<String, Integer> mapStaticPost) {
        this.mapStaticPost = mapStaticPost;
    }

    public Map<String, Integer> getMapStaticBooking() {
        return mapStaticBooking;
    }

    public void setMapStaticBooking(Map<String, Integer> mapStaticBooking) {
        this.mapStaticBooking = mapStaticBooking;
    }
}
