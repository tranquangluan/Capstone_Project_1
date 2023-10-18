package com.example.capstoneproject1.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Booking {
    @Id
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
    private  boolean status;
}
