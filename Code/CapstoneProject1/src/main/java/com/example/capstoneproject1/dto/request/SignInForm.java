package com.example.capstoneproject1.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class SignInForm {
    @NotNull(message = "Email is not null")
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a well-formed email address")
    private String email;

    private String password;

    public SignInForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
