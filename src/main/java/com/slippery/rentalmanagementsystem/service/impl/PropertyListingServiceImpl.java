package com.slippery.rentalmanagementsystem.service.impl;

import com.slippery.rentalmanagementsystem.dto.PropertyListingDto;
import com.slippery.rentalmanagementsystem.mail.PropertyCreation;
import com.slippery.rentalmanagementsystem.mail.RentalAgreementCreation;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyListingServiceImpl implements PropertyListingService {
    private final PropertyRepository repository;
    private final UserRepository userRepository;
    private final PropertyCreation creationEmail;
    private final RentalAgreementRepository rentalAgreementRepository;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final RentalAgreementCreation rentalAgreementCreation;

    public PropertyListingServiceImpl(PropertyRepository repository, UserRepository userRepository, PropertyCreation creationEmail, RentalAgreementRepository rentalAgreementRepository, RentalAgreementCreation rentalAgreementCreation) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.creationEmail = creationEmail;
        this.rentalAgreementRepository = rentalAgreementRepository;
        this.rentalAgreementCreation = rentalAgreementCreation;
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
            propertyListing.setLandlord(owner.get());
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
    public PropertyListingDto addUser(PropertyListingDto request,Long propertyId, Long ownerId,String tenantName) throws IOException {

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
        try{
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

            List<User> tenants =listing.get().getTenants();
            tenants.add(tenant);
            List<RentalAgreement> agreements =listing.get().getRentalAgreements();
            agreements.add(agreement);
            listing.get().setRentalAgreements(agreements);
            repository.save(listing.get());
            rentalAgreementCreation.messageToUser(owner.get().getEmail(),tenant.getEmail(),tenantName,agreement,listing.get());
            rentalAgreementCreation.sendMessageToLandlord(owner.get().getEmail(),owner.get().getUsername(),agreement);

            response.setMessage("New rental agreement created for "+tenantName);
            response.setStatusCode(200);

        }catch (IOException e){
            throw new IOException();
        }

        return response;
    }

    @Override
    public PropertyListingDto removeUser(Long propertyId, Long ownerId, Long tenantId) {
        PropertyListingDto response =new PropertyListingDto();
        Optional<User>owner =userRepository.findById(ownerId);
        Optional<PropertyListing> listing =repository.findById(propertyId);
        Optional<User> tenant =userRepository.findById(tenantId);
        if( tenant.isEmpty()){
            response.setMessage("tenant not found");
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
        if(!listing.get().getTenants().contains(tenant.get())){
            response.setMessage("user does not does not belong to "+listing.get().getName());
            response.setStatusCode(404);
            return response;
        }


        //        remove user from property tenants
        listing.get().getTenants().remove(tenant.get());
        repository.save(listing.get());



//        delete agreement
        List<RentalAgreement> agreement = new ArrayList<>(rentalAgreementRepository.findAll().stream()
                .filter(agreement1 -> agreement1.getTenant().getId().equals(tenant.get().getId()))
                .toList());
        rentalAgreementRepository.delete(agreement.get(0));


        //        delete user
        userRepository.delete(tenant.get());
        response.setMessage("User removed from the system");
        response.setStatusCode(200);
        return response;
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
