package com.slippery.rentalmanagementsystem.service.impl;

import com.slippery.rentalmanagementsystem.dto.PropertyListingDto;
import com.slippery.rentalmanagementsystem.mail.PropertyCreation;
import com.slippery.rentalmanagementsystem.model.PropertyListing;
import com.slippery.rentalmanagementsystem.model.RentalAgreement;
import com.slippery.rentalmanagementsystem.model.User;
import com.slippery.rentalmanagementsystem.repository.PropertyRepository;
import com.slippery.rentalmanagementsystem.repository.RentalAgreementRepository;
import com.slippery.rentalmanagementsystem.repository.UserRepository;
import com.slippery.rentalmanagementsystem.service.PropertyListingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyListingServiceImpl implements PropertyListingService {
    private final PropertyRepository repository;
    private final UserRepository userRepository;
    private final PropertyCreation creationEmail;
    private final RentalAgreementRepository rentalAgreementRepository;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);

    public PropertyListingServiceImpl(PropertyRepository repository, UserRepository userRepository, PropertyCreation creationEmail, RentalAgreementRepository rentalAgreementRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.creationEmail = creationEmail;
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    @Override
    public PropertyListingDto createNewProperty(PropertyListing propertyListing, Long ownerId) {
        PropertyListingDto response =new PropertyListingDto();
        Optional<User> owner =userRepository.findById(ownerId);
        if(owner.isEmpty()){
            response.setErrorMessage("User not found");
            response.setStatusCode(404);
            return response;
        }
        try{
            creationEmail.sendMail(
                    owner.get().getEmail().strip(),
                    owner.get().getUsername(),
                    LocalDateTime.now(),
                    propertyListing
            );
            propertyListing.setCreatedOn(LocalDateTime.now());
            repository.save(propertyListing);
            response.setMessage("New property for "+owner.get().getUsername()+" was created successfully");
            response.setStatusCode(200);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PropertyListingDto updateProperty(PropertyListing propertyListing, Long ownerId, Long propertyId) {
        return null;
    }

    @Override
    public PropertyListingDto addUser(PropertyListingDto request,Long propertyId, Long ownerId,String tenantName) {

        PropertyListingDto response =new PropertyListingDto();
        Optional<User>owner =userRepository.findById(ownerId);
        Optional<PropertyListing> listing =repository.findById(propertyId);
        User user =userRepository.findUserByUsername(tenantName);

        if(user !=null){
            response.setMessage("user is already a tenant");
            response.setStatusCode(404);
            return response;
        }

        if(owner.isEmpty()){
            response.setMessage("owner not found");
            response.setStatusCode(404);
            return response;
        }
        if(listing.isEmpty()){
            response.setMessage("property not found");
            response.setStatusCode(404);
            return response;
        }
        if(!listing.get().getLandlord().getId().equals(owner.get().getId())){
            response.setMessage("property does not belong to "+owner.get().getUsername());
            response.setStatusCode(404);
            return response;
        }
        User tenant =request.getUser();
        tenant.setPassword(passwordEncoder.encode(tenant.getPassword()));
        tenant.setCreatedOn(LocalDateTime.now());
        userRepository.save(tenant);

        RentalAgreement agreement =new RentalAgreement();
        agreement.setLandlord(owner.get());
        agreement.setEndDate(request.getRentalAgreement().getEndDate());
        agreement.setStartOn(request.getRentalAgreement().getStartOn());
        agreement.setMonthlyRent(request.getRentalAgreement().getMonthlyRent());
        agreement.setPropertyListing(listing.get());
        agreement.setSecurityDeposit(request.getRentalAgreement().getSecurityDeposit());
        agreement.setStatus("ACTIVE");
        agreement.setTenant(tenant);
        rentalAgreementRepository.save(agreement);

        response.setMessage("New rental agreement created for "+tenantName);


        return response;
    }

    @Override
    public PropertyListingDto removeUser(Long propertyId, Long ownerId, Long tenantId) {
        return null;
    }

    @Override
    public PropertyListingDto deleteProperty(Long propertyId, Long ownerId) {
        return null;
    }

    @Override
    public PropertyListingDto getPropertyById(Long propertyId, Long ownerId) {
        return null;
    }

    @Override
    public PropertyListingDto getPropertyByName(String name) {
        return null;
    }

    @Override
    public PropertyListingDto getPropertyByLocation(String location) {
        return null;
    }
}
