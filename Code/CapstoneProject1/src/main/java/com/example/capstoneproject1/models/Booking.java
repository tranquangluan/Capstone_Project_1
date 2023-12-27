package com.example.capstoneproject1.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private  User userId;
    @ManyToOne
    @JoinColumn(name = "spaceId")
    private  Space spaceId;
    @Column(name = "totalPrice")
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;
    @Column(name = "bookingDate", nullable = false, updatable = false)
    @CreationTimestamp
    private Date date;
    @Column(name = "paid")
    private BigDecimal paid;

    public Booking(User userId, Space spaceId, BigDecimal totalPrice, Status status, Date date, BigDecimal paid) {
        this.userId = userId;
        this.spaceId = spaceId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
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
}
