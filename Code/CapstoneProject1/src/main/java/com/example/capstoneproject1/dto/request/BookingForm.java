package com.example.capstoneproject1.dto.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BookingForm {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer spaceId;
    @NotNull
    private Integer status;
    public BookingForm() {
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }



}
