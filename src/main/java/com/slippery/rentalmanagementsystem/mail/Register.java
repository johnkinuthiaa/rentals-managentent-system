package com.slippery.rentalmanagementsystem.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class Register {
    private final JavaMailSender javaMailSender;

    public Register(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void SendRegistrationEmail(String to, String username, String email, LocalDateTime registrationDay) throws IOException{
        try {
            SimpleMailMessage mailMessage =new SimpleMailMessage();
            mailMessage.setFrom("");
        } catch (Exception e) {
            throw new IOException(e);
        }

    }
}
