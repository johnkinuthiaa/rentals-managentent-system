package com.slippery.rentalmanagementsystem.mail;

import com.slippery.rentalmanagementsystem.model.PropertyListing;
import com.slippery.rentalmanagementsystem.model.RentalAgreement;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RentalAgreementCreation {
    private final JavaMailSender javaMailSender;

    public RentalAgreementCreation(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void messageToUser(String from, String to, String username, RentalAgreement agreement, PropertyListing propertyListing){
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Welcome to Your New Apartment!");
        message.setText("Dear "+username+",\n" +
                "\n" +
                "Congratulations and welcome to your new apartment at "+propertyListing.getName().toUpperCase()+"\n" +
                "\n" +
                "We are thrilled to have you as part of our community. Here are some important details to help you get started:\n" +
                "\n" +
                "Apartment Address: "+propertyListing.getAddress()+"\n" +
                "Move-in Date: "+agreement.getStartOn()+"\n" +
                "Lease Duration: "+agreement.getEndDate()+"\n" +
                "Monthly Rent: "+agreement.getMonthlyRent()+"\n" +
                "Security Deposit: "+agreement.getSecurityDeposit()+"\n"+
                "Next Steps:\n" +
                "Review Your Lease Agreement: Please take a moment to review your lease agreement, which you can find in your account dashboard.\n" +
                "Set Up Utilities: Make sure to set up your utilities (electricity, water, internet, etc.) before your move-in date.\n" +
                "Schedule a Move-In Inspection: If you would like to schedule a move-in inspection, please contact us at "+propertyListing.getLandlord().getPhone()+".\n" +
                "Community Amenities:\n" +
                "As a resident, you will have access to a variety of amenities, including:\n" +
                "\n" +
                " gym, parking\n" +
                "If you have any questions or need assistance, feel free to reach out to our property management team at "+propertyListing.getLandlord().getPhone()+" or "+propertyListing.getLandlord().getPhone()+". We are here to help!\n" +
                "\n" +
                "Once again, welcome to your new home! We look forward to seeing you around the community.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "The "+propertyListing.getName().toUpperCase() +" community \n");
        javaMailSender.send(message);

    }
    public void sendMessageToLandlord(String to, String username, RentalAgreement agreement, PropertyListing propertyListing){


    }
}
