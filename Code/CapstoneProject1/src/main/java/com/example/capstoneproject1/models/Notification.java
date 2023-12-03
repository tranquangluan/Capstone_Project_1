package com.example.capstoneproject1.models;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationId")
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;

    @Column(name = "type")
    private String type;
    @Column(name = "content")
    private  String content;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private Status status;

    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    public Notification() {
    }


    public Notification(Long notificationId, User sender, User receiver, String type, String content, Status status, Date createdAt) {
        this.notificationId = notificationId;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Notification(User sender, User receiver, String type, String content, Status status) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.content = content;
        this.status = status;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
