package com.ie.email.service;

public interface OtpService {
    public boolean generateOtp(String mobile);

    public boolean verifyMobileOtp(String mobileOtp);
}
