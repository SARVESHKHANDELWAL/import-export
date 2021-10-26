package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;



    public void generateOneTimePassword(User user) {
        //Below portion of code generate random six-digit number to generate otp
        Random rnd=new Random();
        int number=rnd.nextInt(9999);
        String endValue=String.format("%04d",number);
            //Storing the mobile otp to database
            sendOTPMobile(user,endValue);
            user.setOneTimePassword(encoder.encode(endValue));
            user.setOtpRequestedTime(new Date());
            userRepository.save(user);
    }

    public void sendOTPMobile(User user,String otp) {
        //Your user name
        String username = "technott";
        //Your authentication key
        String authkey = "a3fc17aa3cXX";
        //Multiple mobiles numbers separated by comma (max 200)
        //String mobiles = "9503092610";
        //Sender ID.
        String senderId = "TTIOTP";

        //Your message to send, Add URL encoding here.
        String message = "Welcome to Starlike. Your OTP for verification is "+otp+". Please enter this OTP to continue registration.";

        //define route
        String accusage = "1";
        String tempid = "1207161952628218665";

        //Prepare Url
        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;

        //encoding message
        String encoded_message = URLEncoder.encode(message);

        //Send SMS API
        String mainUrl = "http://sms.technotechindia.co.in/sendsms.jsp?";

        //Prepare parameter string
        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append("user=" + username);
        sbPostData.append("&password=" + authkey);
        sbPostData.append("&mobiles=" + user.getMobile());
        sbPostData.append("&sms=" + encoded_message);
        sbPostData.append("&accusage=" + accusage);
        sbPostData.append("&senderid=" + senderId);
        sbPostData.append("&tempid=" + tempid);

        //final string
        mainUrl = sbPostData.toString();
        try {

            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            //reading response
            String response;
            while ((response = reader.readLine()) != null)

                //print response
                System.out.println(response);
            System.out.println(user.getMobile());
            System.out.println(otp);
            //finally close connection
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearOTP(User user) {
        user.setOneTimePassword(null);
        user.setOtpRequestedTime(null);
        userRepository.save(user);
    }
}
