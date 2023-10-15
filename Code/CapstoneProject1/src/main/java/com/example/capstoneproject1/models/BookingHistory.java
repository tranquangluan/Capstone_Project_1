package com.example.capstoneproject1.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class BookingHistory {
    @Id
    @Column(name = "History_booking_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "Space_id")
    private Space spaceId;
    @Column(name = "Total_price")
    private BigDecimal totalPrice;
    @Column(name = "Status")
    private boolean status;
    @Column(name = "Booking_date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "Invoice_id")
    private Invoice invoiceId;
}
