package com.slippery.rentalmanagementsystem.mail;

import lombok.Value;
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
            mailMessage.setFrom("johnmuniu477@gmail.com");
            mailMessage.setTo(email.strip());
            mailMessage.setSubject("Welcome to the Rental Management System!");
            mailMessage.setText(
                    "Dear "+username+" ,\n" +
                            "\n" +
                            "Thank you for registering with the Rental Management System ! We are excited to have you on board and look forward to helping you manage your rental needs seamlessly.\n" +
                            "\n" +
                            "Your Account Details:\n" +
                            "Username: "+username+"\n" +
                            "Email: "+email+" \n" +
                            "Registration Date: "+registrationDay+"\n" +
                            "Getting Started:\n" +
                            "To help you get started, here are a few steps you can take:\n" +
                            "\n" +
                            "Complete Your Profile: Make sure to fill out your profile with accurate information to enhance your experience.\n" +
                            "Explore Our Features: Check out our features, including property listings, rental agreements, payment tracking, and maintenance requests.\n" +
                            "Need Help?\n" +
                            "If you have any questions or need assistance, our support team is here to help! You can reach us at "+mailMessage.getFrom()+" or 07173___.\n" +
                            "\n" +
                            "Stay Connected:\n" +

                            "\n" +
                            "Thank you for choosing Us. We are committed to providing you with the best rental management experience!\n" +
                            "\n" +
                            "Best regards,\n" +
                            "\n" +
                            "[Kinuthia john]\n" +
                            "[CTO]\n"
//                            "[Your Company Contact Information]\n" +
//                            "[Your Company Website]"
            );
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new IOException(e);
        }

    }
}
