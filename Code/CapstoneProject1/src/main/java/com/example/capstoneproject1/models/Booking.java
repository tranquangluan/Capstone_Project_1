package com.example.capstoneproject1.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Booking {
    @Id
    @Column(name = "Booking_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private  User userId;
    @ManyToOne
    @JoinColumn(name = "Space_id")
    private  Space spaceId;
    @Column(name = "Total_price")
    private BigDecimal totalPrice;
    @Column(name = "status")
    private  boolean status;
}
