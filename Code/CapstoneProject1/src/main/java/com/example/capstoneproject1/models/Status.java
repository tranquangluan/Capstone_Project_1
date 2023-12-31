package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Status {
    @Id
    @Column(name="statusId", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name="status", columnDefinition = "nvarchar(30)")
    private String status;
    public Status() {
    }

    public Status(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
