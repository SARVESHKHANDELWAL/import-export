package com.ie.email.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    public boolean sendVerificationEmail(String emailId) throws MessagingException, UnsupportedEncodingException;
    public boolean verifyEmail(String emailOtp);
}
