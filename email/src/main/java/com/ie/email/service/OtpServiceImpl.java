package com.ie.email.service;

import com.ie.email.model.User;
import com.ie.email.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private UserRepository repo;


    @Override
    public boolean generateOtp(String mobile) {
        sendMessage(mobile);
        return  true;
    }

    @Override
    public boolean verifyMobileOtp(String mobileOtp) {
        if(repo.existsByMobileOtp(mobileOtp)) {
            {
                User user = repo.findByMobileOtp(mobileOtp);

                if (user == null || user.isEnabled()) {
                    return false;
                } else {
                    user.setMobileOtp(null);
                    user.setEnabled(true);
                    repo.save(user);
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    public void sendMessage(String mobiles) {

        //Your user name
        String username = "technott";
        //Your authentication key
        String authkey = "a3fc17aa3cXX";
        //Multiple mobiles numbers separated by comma (max 200)
        //String mobiles = "9503092610";
        //Sender ID.
        String senderId = "TTIOTP";

        //Below portion of code generate random six-digit number to generate otp
        Random rnd=new Random();
        int number=rnd.nextInt(9999);
        String endValue=String.format("%04d",number);

        //Storing the mobile otp to database
        User user=new User();
        user.setMobileOtp(endValue);
        repo.save(user);

        //Your message to send, Add URL encoding here.
        String message = "Welcome to Starlike. Your OTP for verification is "+endValue+". Please enter this OTP to continue registration.";

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
        sbPostData.append("&mobiles=" + mobiles);
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
//finally close connection
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
