package com.example.capstoneproject1.dto.response.sharing;

import com.example.capstoneproject1.models.Sharing;

import java.util.List;

public class ListSharesResponse {
    private Integer error;
    private String message;
    private Integer spaceQuantity;

    private List<Sharing> listSpaces;

    private Integer status;

    public ListSharesResponse() {
    }

    public ListSharesResponse(Integer error, String message, Integer spaceQuantity, List<Sharing> listSpaces, Integer status) {
        this.error = error;
        this.message = message;
        this.spaceQuantity = spaceQuantity;
        this.listSpaces = listSpaces;
        this.status = status;
    }

    public ListSharesResponse(Integer error, String message, Integer spaceQuantity, Integer status) {
        this.error = error;
        this.message = message;
        this.spaceQuantity = spaceQuantity;
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

    public Integer getSpaceQuantity() {
        return spaceQuantity;
    }

    public void setSpaceQuantity(Integer spaceQuantity) {
        this.spaceQuantity = spaceQuantity;
    }

    public List<Sharing> getListSpaces() {
        return listSpaces;
    }

    public void setListSpaces(List<Sharing> listSpaces) {
        this.listSpaces = listSpaces;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
