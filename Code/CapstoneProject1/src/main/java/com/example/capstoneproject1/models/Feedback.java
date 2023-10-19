package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Feedback {
    @Id
    @Column(name = "feedBackId")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "userSendFeedBackId")
    private User userSendFeedBackId;
    @ManyToOne
    @JoinColumn(name = "userReceiveFeedBackId")
    private User userReceiveFeedBackId;
    @Column(name = "rate")
    private String rate;
    @Column(name = "comment")
    private String comment;
}
