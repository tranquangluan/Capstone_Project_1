package com.example.capstoneproject1.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDTO {

    private String zpTransId;
    private Long amount;
    private String description;

    public String getZpTransId() {
        return zpTransId;
    }

    public void setZpTransId(String zpTransId) {
        this.zpTransId = zpTransId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
