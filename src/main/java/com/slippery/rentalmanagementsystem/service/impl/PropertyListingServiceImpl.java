package com.slippery.rentalmanagementsystem.service.impl;

import com.slippery.rentalmanagementsystem.dto.PropertyListingDto;
import com.slippery.rentalmanagementsystem.mail.PropertyCreation;
import com.slippery.rentalmanagementsystem.model.PropertyListing;
import com.slippery.rentalmanagementsystem.model.User;
import com.slippery.rentalmanagementsystem.repository.PropertyRepository;
import com.slippery.rentalmanagementsystem.repository.UserRepository;
import com.slippery.rentalmanagementsystem.service.PropertyListingService;
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

    public PropertyListingServiceImpl(PropertyRepository repository, UserRepository userRepository, PropertyCreation creationEmail) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.creationEmail = creationEmail;
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
    public PropertyListingDto addUser(Long propertyId, Long ownerId, Long tenantId) {
//        start hereeeee
        PropertyListingDto response =new PropertyListingDto();
        Optional<User>owner =userRepository.findById(ownerId);
        Optional<User>tenant =userRepository.findById(tenantId);
        Optional<PropertyListing> listing =repository.findById(propertyId);
        User newTenant =new User();


        return null;
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
