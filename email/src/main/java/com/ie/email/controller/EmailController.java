package com.ie.email.controller;


import com.ie.email.repository.UserRepository;
import com.ie.email.response.MessageResponse;
import com.ie.email.response.Success;
import com.ie.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;



    @GetMapping("/sendemail")
    public Object sendEmail(@Param("email") String email) throws MessagingException, UnsupportedEncodingException {
        Success success=new Success();
        if (emailService.sendVerificationEmail(email)) {
            success.setSuccess("Successfully send email");
        } else {
            return new RuntimeException("failed to send email");
            // success.setSuccess("failed to send email");
        }
        return success;
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyUser(@Param("otp") String otp) {
       if(!userRepository.existsByEmailOtp(otp)){
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error: Entered wrong otp"));
       }

        Success success=new Success();
        if (emailService.verifyEmail(otp)) {
            success.setSuccess("Successfully verify email");

        } else {
            success.setSuccess("failed to verify email");
        }
        return ResponseEntity.ok(new MessageResponse(success.getSuccess()));
    }
}
