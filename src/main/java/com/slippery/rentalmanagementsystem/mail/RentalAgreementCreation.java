package com.slippery.rentalmanagementsystem.mail;

import com.slippery.rentalmanagementsystem.model.PropertyListing;
import com.slippery.rentalmanagementsystem.model.RentalAgreement;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RentalAgreementCreation {
    private final JavaMailSender javaMailSender;

    public RentalAgreementCreation(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void messageToUser(String from, String to, String username, RentalAgreement agreement, PropertyListing propertyListing) throws IOException {
        try{
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
        }catch (Exception e){
            throw new IOException();
        }


    }
    public void sendMessageToLandlord(String to, String username, RentalAgreement agreement) throws IOException {
        try{
            SimpleMailMessage message =new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("New Tenant Registration Notification");
            message.setText(
                    "Dear "+username+",\n" +
                            "\n" +
                            "We are pleased to inform you that a new tenant has successfully registered for your property at "+agreement.getPropertyListing().getName()+". Here are the details of the new tenant:\n" +
                            "\n" +
                            "Tenant Name: "+agreement.getTenant().getUsername() +
                            "Tenant Email: "+agreement.getTenant().getEmail()+"\n" +
                            "Tenant Phone Number: "+agreement.getTenant().getPhone()+"\n" +
                            "Lease Start Date: "+agreement.getStartOn()+"\n" +
                            "Lease Duration: "+agreement.getEndDate()+"\n" +
                            "Monthly Rent: "+agreement.getMonthlyRent()+"\n" +
                            "Security Deposit: "+agreement.getSecurityDeposit()+"\n" +
                            "Next Steps:\n" +
                            "Review the Lease Agreement: Please ensure that you have reviewed and signed the lease agreement with the tenant.\n" +
                            "Schedule a Move-In Inspection: If you would like to schedule a move-in inspection, please coordinate with the tenant to find a suitable time.\n" +
                            "Prepare for Move-In: Make sure the property is ready for the tenant's arrival, including any necessary cleaning or maintenance.\n" +
                            "Thank you for being a valued part of our community. We look forward to a successful rental experience for both you and your new tenant.\n" +
                            "\n" +
                            "Best regards,\n" +
                            "\n" +
                            "[Your Company Name]\n" +
                            "[Your Company Contact Information]\n" +
                            "[Your Company Website]\n" +
                            "\n");
        } catch (Exception e) {
            throw new IOException(e);
        }

    }
}
