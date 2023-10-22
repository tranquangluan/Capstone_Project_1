package com.example.capstoneproject1.models;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Integer id;
    @Column(name = "userName", unique = true,nullable = false)
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(columnDefinition = "TEXT", name = "Password",nullable = false)
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

    public User(Integer id, String name, String gender, Date dateOfBirth, String phone, String email, String password, String avatar, String province, String district, String ward, String address, Role roleCode) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
        this.roleCode = roleCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Role roleCode) {
        this.roleCode = roleCode;
    }
}
