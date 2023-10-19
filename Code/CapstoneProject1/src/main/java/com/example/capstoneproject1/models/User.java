package com.example.capstoneproject1.models;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Integer id;
    @Column(name = "userName")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(columnDefinition = "TEXT", name = "Password")
    private String password;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "ward")
    private String ward;
    @Column(name = "address")
    private String address;
    @ManyToOne
    @JoinColumn(name = "roleCode")
    private Role roleCode;

    public User() {
    }


}
