package com.example.capstoneproject1.dto.response.dashboard;

import com.example.capstoneproject1.models.Space;

import java.util.List;
import java.util.Map;

public class StaticResponse {
    private Integer error;
    private String message;

    private Map<String,Integer> totalMap;
    private Integer status;


    public StaticResponse(Integer error, String message, Map<String, Integer> totalMap, Integer status) {
        this.error = error;
        this.message = message;
        this.totalMap = totalMap;
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

    public Map<String, Integer> getTotalMap() {
        return totalMap;
    }

    public void setTotalMap(Map<String, Integer> totalMap) {
        this.totalMap = totalMap;
    }
}
