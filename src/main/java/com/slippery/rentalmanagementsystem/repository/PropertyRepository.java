package com.slippery.rentalmanagementsystem.repository;

import com.slippery.rentalmanagementsystem.model.PropertyListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<PropertyListing,Long> {

}
