package com.example.capstoneproject1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @Column(name = "roleCode" , length = 10)
    @JsonIgnore
    private String roleCode;
    @Column(name = "roleValue" , length = 10)
    private String roleValue;

    public Role() {
    }

    public Role(String roleCode, String roleValue) {
        this.roleCode = roleCode;
        this.roleValue = roleValue;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }
}
