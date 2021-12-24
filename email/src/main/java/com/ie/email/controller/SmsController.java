package com.ie.email.controller;


import com.ie.email.repository.UserRepository;
import com.ie.email.response.MessageResponse;
import com.ie.email.response.Success;
import com.ie.email.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/sms")
public class SmsController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository repository;

    @GetMapping("/sendSms")
    public Success sendSms(@Param("mobile") String mobile){
        Success success=new Success();
        if (otpService.generateOtp(mobile)) {
            success.setSuccess("Successfully send otp to mobile");
        } else {
            success.setSuccess("failed to send otp at mobile");
        }
        return success;
    }

    @GetMapping("/verifyMobile")
    public ResponseEntity<?> verifyUser(@Param("otp") String otp) {
        if(!repository.existsByMobileOtp(otp)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Entered wrong otp"));
        }

        if (otpService.verifyMobileOtp(otp)) {
            return ResponseEntity
                    .ok(new MessageResponse("Mobile verified successfully"));
        }
        return ResponseEntity.ok(new MessageResponse("some issue with verification service"));
    }
}
