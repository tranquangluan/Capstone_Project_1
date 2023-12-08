package com.example.capstoneproject1.dto.response.feedback;

import com.example.capstoneproject1.models.Feedback;

import java.util.List;

public class PageFeedback {
    private Integer totalPages;
    private List<Feedback> listFeedbacks;

    public PageFeedback() {
    }

    public PageFeedback(Integer totalPages, List<Feedback> listFeedbacks) {
        this.totalPages = totalPages;
        this.listFeedbacks = listFeedbacks;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Feedback> getListFeedbacks() {
        return listFeedbacks;
    }

    public void setListFeedbacks(List<Feedback> listFeedbacks) {
        this.listFeedbacks = listFeedbacks;
    }
}
