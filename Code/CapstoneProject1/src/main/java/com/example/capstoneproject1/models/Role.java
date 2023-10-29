package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @Column(name = "roleCode")
    private String roleCode;
    @Column(name = "roleValue")
    private String roleValue;

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
