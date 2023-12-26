package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class BookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingHistoryId")
    private Integer id;
}
