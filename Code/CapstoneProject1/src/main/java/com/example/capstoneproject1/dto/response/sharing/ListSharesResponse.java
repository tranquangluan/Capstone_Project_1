package com.example.capstoneproject1.dto.response.sharing;

import com.example.capstoneproject1.models.Sharing;

import java.util.List;

public class ListSharesResponse {
    private Integer error;
    private String message;
    private Integer sharingQuantity;

    private List<Sharing> listSharing;

    private Integer status;

    public ListSharesResponse() {
    }

    public ListSharesResponse(Integer error, String message, Integer sharingQuantity, List<Sharing> listSharing, Integer status) {
        this.error = error;
        this.message = message;
        this.sharingQuantity = sharingQuantity;
        this.listSharing = listSharing;
        this.status = status;
    }

    public ListSharesResponse(Integer error, String message, Integer sharingQuantity, Integer status) {
        this.error = error;
        this.message = message;
        this.sharingQuantity = sharingQuantity;
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSharingQuantity() {
        return sharingQuantity;
    }

    public void setSharingQuantity(Integer sharingQuantity) {
        this.sharingQuantity = sharingQuantity;
    }

    public List<Sharing> getListSharing() {
        return listSharing;
    }

    public void setListSharing(List<Sharing> listSharing) {
        this.listSharing = listSharing;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
