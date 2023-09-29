package com.example.capstoneproject1.models;
import javax.persistence.*;

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

    public User() {
    }

    public User(Integer userId, String userName, String password, String email) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
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
}
