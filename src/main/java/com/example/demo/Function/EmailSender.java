package com.example.demo.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendOTPEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pdoanthuannb69@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
        System.out.println("Mail Send...");
    }

    public static String GenOTP(String email) {
        if (email == null || email.length() < 3) {
            return "532253";
        } else {
            char Char1 = email.charAt(0);
            char Char2 = email.charAt(1);
            char Char3 = email.charAt(2);
            String result = String.format("%03d%03d%03d", (int) Char1, (int) Char2, (int) Char3);
            return result.substring(0,6);
        }
    }
}
