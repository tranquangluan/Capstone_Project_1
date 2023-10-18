package com.example.capstoneproject1.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Location {
    @Id
    @Column(name = "locationId")
    private Integer id;
    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "ward")
    private String ward;
    @Column(name = "street")
    private String street;

}
