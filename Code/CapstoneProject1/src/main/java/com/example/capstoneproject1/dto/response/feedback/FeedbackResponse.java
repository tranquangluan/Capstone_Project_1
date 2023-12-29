package com.example.capstoneproject1.dto.response.feedback;

import com.example.capstoneproject1.models.Feedback;

public class FeedbackResponse {

    private Integer error;

    private String message;

    private Feedback feedback;

    private Integer status;

    public FeedbackResponse() {
    }

    public FeedbackResponse(Integer error, String message, Feedback feedback, Integer status) {
        this.error = error;
        this.message = message;
        this.feedback = feedback;
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

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

