package com.example.capstoneproject1.models;


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
    @Column(name = "status")
    private String status;
    @Column(name = "bookingDate", updatable = false ,nullable = false)
    private Date date;

    public Booking() {
    }

    public Booking(User userId, Space spaceId, BigDecimal totalPrice) {
        this.userId = userId;
        this.spaceId = spaceId;
        this.totalPrice = totalPrice;
    }

    public Booking(User userId, Space spaceId, BigDecimal totalPrice, String status, Date date) {
        this.userId = userId;
        this.spaceId = spaceId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.date = date;
    }

    public Booking(Integer id, User userId, Space spaceId, BigDecimal totalPrice, String status) {
        this.id = id;
        this.userId = userId;
        this.spaceId = spaceId;
        this.totalPrice = totalPrice;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}