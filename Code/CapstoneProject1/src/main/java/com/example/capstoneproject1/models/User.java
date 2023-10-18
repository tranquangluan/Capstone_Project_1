package com.example.capstoneproject1.models;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Integer userId;
    @Column(name = "userName")
    private String userName;
    @Column(columnDefinition = "TEXT", name = "Password")
    private String password;
    @Column(name = "email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location locationId;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "phone")
    private Integer phone;
    @Column(name = "avatar")
    private String avatar;
    @ManyToOne
    @JoinColumn(name = "roleCode")
    private Role roleCode;

    public User() {
    }

    public User(Integer userId, String userName, String password, String email, Location locationId, Date dateOfBirth, Integer phone, String avatar, Role roleCode) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.locationId = locationId;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.avatar = avatar;
        this.roleCode = roleCode;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date date_of_birth) {
        this.dateOfBirth = date_of_birth;
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

    public Role getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Role code) {
        this.roleCode = code;
    }
}
