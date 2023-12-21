package com.example.capstoneproject1.dto.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BookingForm {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer spaceId;
    @NotNull
    private BigDecimal totalPrice;
    public BookingForm() {
    }

    public BookingForm(Integer userId, Integer spaceId, BigDecimal totalPrice) {
        this.userId = userId;
        this.spaceId = spaceId;
        this.totalPrice = totalPrice;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}
