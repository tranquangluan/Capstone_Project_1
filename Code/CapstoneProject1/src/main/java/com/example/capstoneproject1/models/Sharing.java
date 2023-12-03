package com.example.capstoneproject1.models;

import javax.persistence.*;

@Entity
public class Sharing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharingId")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "spaceId")
    private Space spaceId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;
    @Column(name = "infoSharing", columnDefinition = "TEXT")
    private String infoSharing;
    @ManyToOne
    @JoinColumn(name = "statusId")
    private Status status;

    public Sharing() {
    }

    public Sharing(Integer id, Space spaceId, User userId, String infoSharing, Status status) {
        this.id = id;
        this.spaceId = spaceId;
        this.userId = userId;
        this.infoSharing = infoSharing;
        this.status = status;
    }

    public Sharing(Space spaceId, User userId, String infoSharing, Status status) {
        this.spaceId = spaceId;
        this.userId = userId;
        this.infoSharing = infoSharing;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Space getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Space spaceId) {
        this.spaceId = spaceId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getInfoSharing() {
        return infoSharing;
    }

    public void setInfoSharing(String infoSharing) {
        this.infoSharing = infoSharing;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
