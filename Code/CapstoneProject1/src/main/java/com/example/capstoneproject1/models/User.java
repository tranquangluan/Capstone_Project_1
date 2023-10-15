package com.example.capstoneproject1.models;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Integer userId;
    @Column(name = "User_Name")
    private String userName;
    @Column(columnDefinition = "TEXT", name = "Password")
    private String password;
    @Column(name = "Email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "Location_id")
    private Location locationId;
    @Column(name = "DateOfBirth")
    private Date date_of_birth;
    @Column(name = "Phone")
    private Integer phone;
    @Column(name = "Avatar")
    private String avatar;
    @ManyToOne
    @JoinColumn(name = "Role_code")
    private Role code;

    public User() {
    }

    public User(Integer userId, String userName, String password, String email, Location locationId, Date date_of_birth, Integer phone, String avatar, Role code) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.locationId = locationId;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.avatar = avatar;
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getCode() {
        return code;
    }

    public void setCode(Role code) {
        this.code = code;
    }
}
