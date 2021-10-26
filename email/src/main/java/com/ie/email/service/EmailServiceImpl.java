package com.ie.email.service;

import com.ie.email.model.User;
import com.ie.email.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private UserRepository repo;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public boolean sendVerificationEmail(String emailId)  throws MessagingException, UnsupportedEncodingException {

        //It will generate the random code for email otp
        Random rnd=new Random();
        int number=rnd.nextInt(9999);
        String randomCode=String.format("%04d",number);

        //save the data into user_new table for email otp
        User user=new User();
        user.setEmailOtp(randomCode);
        repo.save(user);

        //send the email with contents
        String toAddress = emailId;
        System.out.print(toAddress);
        String fromAddress = "gajananpoul47@gmail.com";
        String senderName = "TecHere Pvt. Ltd.";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please enter the otp to verify your registration:<br>"
                + "<h3>"+randomCode+"</h3>"
                + "Thank you,<br>"
                + "Your company name.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", "Gajanan Poul");
        helper.setText(content, true);

        mailSender.send(message);

        return true;
    }


    @Override
    public boolean verifyEmail(String emailOtp) {
        if (repo.existsByEmailOtp(emailOtp)) {
            User user = repo.findByEmailOtp(emailOtp);

            if (user == null || user.isEnabled()) {
                return false;
            } else {
                user.setEmailOtp(null);
                user.setEnabled(true);
                repo.save(user);

                return true;
            }
        }else {
        return false;
        }
    }
}
