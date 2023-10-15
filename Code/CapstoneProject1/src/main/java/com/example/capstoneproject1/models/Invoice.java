package com.example.capstoneproject1.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Invoice {
    @Id
    @Column(name = "Invoice_Id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "Booking_id")
    private Booking bookingId;
    @Column(name = "Invoice_date")
    private Date invoiceDate;
    @Column(name = "Amount")
    private Integer amount;
    @Column(name = "Payment_status")
    private boolean status;
}
