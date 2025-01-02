package com.slippery.rentalmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.slippery.rentalmanagementsystem.model.PropertyListing;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyListingDto {
    private String message;
    private String errorMessage;
    private int statusCode;
    private PropertyListing listing;
    private List<PropertyListing> propertyListings;
}
