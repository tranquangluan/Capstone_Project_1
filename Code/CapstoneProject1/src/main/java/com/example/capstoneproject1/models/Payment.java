package com.example.capstoneproject1.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Payment {
    @Id
    @Column(name = "Payment_Id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "Booking_id")
    private Booking bookingId;
    @Column(name = "Payment_date")
    private Date date;
    @Column(name = "Amount")
    private Integer amount;
    @Column(name = "Payment_method")
    private String method;
}
