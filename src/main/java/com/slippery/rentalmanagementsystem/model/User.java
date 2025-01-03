package com.slippery.rentalmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String phone;
    private String address;
    private LocalDateTime createdOn;
    private Boolean isActive =false;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PropertyListing> propertyListings;
}
