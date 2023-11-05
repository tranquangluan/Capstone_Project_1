package com.example.capstoneproject1.dto.response;

import com.example.capstoneproject1.models.Favourite;

public class FavoriteResponse {

    private Integer error;
    private String message;
    private Favourite favorite;
    private Integer status;

    public FavoriteResponse() {
    }

    public FavoriteResponse(Integer error, String message, Favourite favorite, Integer statusCode) {
        this.error = error;
        this.message = message;
        this.favorite = favorite;
        this.status = statusCode;
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

    public Favourite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favourite favorite) {
        this.favorite = favorite;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
