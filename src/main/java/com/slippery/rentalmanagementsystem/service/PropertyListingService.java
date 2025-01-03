package com.slippery.rentalmanagementsystem.service;

import com.slippery.rentalmanagementsystem.dto.PropertyListingDto;
import com.slippery.rentalmanagementsystem.model.PropertyListing;
import com.slippery.rentalmanagementsystem.model.User;

public interface PropertyListingService {
    PropertyListingDto createNewProperty(PropertyListing propertyListing,Long ownerId);
    PropertyListingDto updateProperty(PropertyListing propertyListing,Long ownerId,Long propertyId);
    PropertyListingDto addUser(PropertyListingDto dto,Long propertyId, Long ownerId,String tenantName);
    PropertyListingDto removeUser(Long propertyId,Long ownerId,Long tenantId);
    PropertyListingDto deleteProperty(Long propertyId,Long ownerId);
    PropertyListingDto getPropertyById(Long propertyId,Long ownerId);
    PropertyListingDto getPropertyByName(String name);
    PropertyListingDto getPropertyByLocation(String location);


}
