package com.example.capstoneproject1.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedBackId")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "senderFeedBackId")
    private User userSendFeedBack;
    @ManyToOne
    @JoinColumn(name = "receiverFeedBackId")
    private User userReceiveFeedBack;
    @Column(name = "rate")
    @Min(0)
    @Max(5)
    private Integer rate;
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    public Feedback() {
    }

    public Feedback(Integer id, User userSendFeedBack, User userReceiveFeedBack, Integer rate, String comment, Timestamp createdAt) {
        this.id = id;
        this.userSendFeedBack = userSendFeedBack;
        this.userReceiveFeedBack = userReceiveFeedBack;
        this.rate = rate;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Feedback(User userSendFeedBack, User userReceiveFeedBack, Integer rate, String comment) {
        this.userSendFeedBack = userSendFeedBack;
        this.userReceiveFeedBack = userReceiveFeedBack;
        this.rate = rate;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserSendFeedBack() {
        return userSendFeedBack;
    }

    public void setUserSendFeedBack(User userSendFeedBack) {
        this.userSendFeedBack = userSendFeedBack;
    }

    public User getUserReceiveFeedBack() {
        return userReceiveFeedBack;
    }

    public void setUserReceiveFeedBack(User userReceiveFeedBack) {
        this.userReceiveFeedBack = userReceiveFeedBack;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
