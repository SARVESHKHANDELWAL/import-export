package com.ie.loginorsignup.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AvailabilityRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String companyName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String mobile;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
