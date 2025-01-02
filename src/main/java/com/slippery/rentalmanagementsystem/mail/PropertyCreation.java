package com.slippery.rentalmanagementsystem.mail;

import com.slippery.rentalmanagementsystem.model.PropertyListing;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PropertyCreation {
    private final JavaMailSender mailSender;

    public PropertyCreation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendMail(String to, String username, LocalDateTime createdOn, PropertyListing listing) throws IOException {
        SimpleMailMessage mailMessage =new SimpleMailMessage();
        try {
            mailMessage.setTo(to);
            mailMessage.setFrom("johnmuniu477@gmail.com");
            mailMessage.setSubject("Success creating property listing!");
            mailMessage.setText(
                    "Dear "+username+",\n" +
                            "\n" +
                            "Congratulations! Your property listing has been successfully created on Nevani Property Management. We’re excited to help you connect with potential renters.\n" +
                            "\n" +
                            "Listing Details:\n" +
                            "Property Title: "+listing.getName()+"\n" +
                            "Location: "+listing.getLocation()+"\n" +
                            "Type: "+listing.getType()+"\n" +
                            "Price: "+listing.getPrice()+"\n" +
                            "Next Steps:\n" +
                            "Review Your Listing: You can view and edit your listing at any time by logging into your account //link to listing.\n" +
                            "Promote Your Listing: Share your listing on social media or with friends to attract more interest.\n" +
                            "Manage Inquiries: Be prepared to respond to inquiries from potential renters. You can manage messages directly through your account.\n" +
                            "If you have any questions or need assistance, feel free to reach out to our support team at  0717...... We’re here to help!\n" +
                            "\n" +
                            "Thank you for choosing Nevani Property Management. We wish you the best of luck with your property listing!\n" +
                            "\n" +
                            "Best regards,\n" +
                            "\n" +
                            "Nevani Property Management\n" +
                            "https://nevani-housing.onrender.com/about\n" +
                            "https://nevani-housing.onrender.com/\n" +
                            "\n");
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new IOException(e);
        }

    }
}
