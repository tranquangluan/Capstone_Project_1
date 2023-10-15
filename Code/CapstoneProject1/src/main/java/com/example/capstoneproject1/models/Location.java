package com.example.capstoneproject1.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Location {
    @Id
    @Column(name = "Location_id")
    private Integer id;
    @Column(name = "Province")
    private String province;
    @Column(name = "District")
    private String district;
    @Column(name = "Ward")
    private String ward;
    @Column(name = "Street")
    private String street;

}
