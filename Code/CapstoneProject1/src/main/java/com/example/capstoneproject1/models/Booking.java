package com.example.capstoneproject1.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId", columnDefinition = "int")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "userId", columnDefinition = "int")
    private  User userId;
    @ManyToOne
    @JoinColumn(name = "spaceId", columnDefinition = "int")
    private  Space spaceId;
    @Column(name = "totalPrice", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;
    @Column(name = "bookingDate", columnDefinition = "DATETIME", nullable = false, updatable = false)
    @CreationTimestamp
    private Date date;
    @Column(name = "dateArrive", columnDefinition = "DATETIME", nullable = false)
    private Date dateArrive;
    @Column(name = "comment", columnDefinition = "nvarchar(255)")
    private String comment;
    @Column(name = "paid", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal paid;

    public Booking(User userId, Space spaceId, BigDecimal totalPrice, Status status, Date dateArrive, String comment, BigDecimal paid) {
        this.userId = userId;
        this.spaceId = spaceId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.dateArrive = dateArrive;
        this.comment = comment;
        this.paid = paid;
    }

    public Booking(User userId, Space spaceId, BigDecimal totalPrice, Status status, BigDecimal paid) {
        this.userId = userId;
        this.spaceId = spaceId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.paid = paid;
    }

    public Booking() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Space getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Space spaceId) {
        this.spaceId = spaceId;
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
