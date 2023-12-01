package com.example.capstoneproject1.dto.request;

import javax.validation.constraints.NotNull;

public class VerifyEmailForm {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String province;
    @NotNull
    private String district;
    @NotNull
    private String ward;
    @NotNull
    private String address;
    @NotNull
    private String otp;

    public VerifyEmailForm() {
    }

    public VerifyEmailForm(String name, String email, String password, String province, String district, String ward, String address, String otp) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
        this.otp = otp;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getWard() {
        return ward;
    }

    public String getAddress() {
        return address;
    }

    public String getOtp() {
        return otp;
    }
}
