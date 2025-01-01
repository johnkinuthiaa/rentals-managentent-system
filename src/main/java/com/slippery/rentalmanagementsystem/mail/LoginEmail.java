package com.slippery.rentalmanagementsystem.mail;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class LoginEmail {
    /**
     * author @john kinuthia
     * send email to user every time they log in to their account
     */
    private final JavaMailSender mailSender;
    private final HttpServletRequest httpServletRequest;

    public LoginEmail(JavaMailSender mailSender, HttpServletRequest httpServletRequest){
        this.mailSender = mailSender;
        this.httpServletRequest = httpServletRequest;
    }
    public void sendEmail(String to,String username) throws IOException, RuntimeException,UnknownHostException {
        try{
            String systemName =InetAddress.getLocalHost().getHostName();
            String ipAddress =InetAddress.getLocalHost().getHostAddress();
            String browser = httpServletRequest.getHeader("user-agent");

            SimpleMailMessage email =new SimpleMailMessage();
            email.setSubject("New login alert");
            email.setTo(to);
            email.setFrom("johnmuniu477@gmail.com");
            email.setSentDate(new Date(System.currentTimeMillis()));
            email.setText("Dear "+username+ ",\n" +
                    "\n" +
                    "We wanted to inform you that your account has been accessed recently. Here are the details of the login:\n" +
                    "\n" +
                    "Date and Time: "+LocalDateTime.now()+"\n" +
                    "Location: "+ipAddress+"\n" +
                    "Device: "+systemName+"\n" +
                    "Browser: "+browser+" \n" +
                    "If this was you, no further action is needed. However, if you did not authorize this login, we recommend that you take the following steps immediately:\n" +
                    "\n" +
                    "Change Your Password: Log in to your account and update your password to something secure.\n" +
                    "Review Your Account Activity: Check for any unauthorized transactions or changes.\n" +
                    "Enable Two-Factor Authentication (2FA): For added security, consider enabling 2FA on your account.\n" +
                    "If you have any questions or need assistance, please do not hesitate to contact our support team at [Support Email] or [Support Phone Number].\n" +
                    "\n" +
                    "Thank you for being a valued member of our community!\n" +
                    "\n" +
                    "Best regards,\n" +
                    "\n" +
                    "[Your Company Name]\n" +
                    "[Your Company Contact Information]\n" +
                    "[Your Company Website]");
            mailSender.send(email);

        }catch (Exception e){
            throw new IOException(e);
        }

    }
}
