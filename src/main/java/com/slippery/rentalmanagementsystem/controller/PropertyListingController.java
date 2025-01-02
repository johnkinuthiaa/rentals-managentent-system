package com.slippery.rentalmanagementsystem.controller;

import com.slippery.rentalmanagementsystem.dto.PropertyListingDto;
import com.slippery.rentalmanagementsystem.model.PropertyListing;
import com.slippery.rentalmanagementsystem.service.PropertyListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyListingController {
    private final PropertyListingService service;

    public PropertyListingController(PropertyListingService service) {
        this.service = service;
    }
    @PostMapping("/create/new")
    public ResponseEntity<PropertyListingDto> createNewProperty(
            @RequestBody PropertyListing propertyListing,
            @RequestParam Long ownerId){
        return ResponseEntity.ok(service.createNewProperty(propertyListing, ownerId));

    }
}