package com.example.capstoneproject1.dto.response.feedback;

import com.example.capstoneproject1.models.Feedback;

import java.util.List;

public class ListFeedbackResponse {

    private Integer error;
    private String message;
    private Integer feedbackQuantity;
    private Integer totalPages;
    private Integer averageRate;

    private List<Feedback> listFeedbacks;

    private Integer status;

    public ListFeedbackResponse() {
    }


    public ListFeedbackResponse(Integer error, String message, Integer feedbackQuantity, Integer totalPages, Integer averageRate, List<Feedback> listFeedbacks, Integer status) {
        this.error = error;
        this.message = message;
        this.feedbackQuantity = feedbackQuantity;
        this.totalPages = totalPages;
        this.averageRate = averageRate;
        this.listFeedbacks = listFeedbacks;
        this.status = status;
    }

    public ListFeedbackResponse(Integer error, String message, Integer totalPages, Integer averageRate, Integer status) {
        this.error = error;
        this.message = message;
        this.totalPages = totalPages;
        this.averageRate = averageRate;
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

    public Integer getFeedbackQuantity() {
        return feedbackQuantity;
    }

    public void setFeedbackQuantity(Integer feedbackQuantity) {
        this.feedbackQuantity = feedbackQuantity;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Integer averageRate) {
        this.averageRate = averageRate;
    }

    public List<Feedback> getListFeedbacks() {
        return listFeedbacks;
    }

    public void setListFeedbacks(List<Feedback> listFeedbacks) {
        this.listFeedbacks = listFeedbacks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
