package com.slippery.rentalmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    name if its an apartment or rental
    private String name;
    private String location;
    private String address;
    private String type;
    private Long price;
    @Lob
    private String description;
    private String status;
    private String images;
    private LocalDateTime createdOn;
    @ManyToOne
    private User user;
//    if its a rental
    @ManyToMany
    private List<User> tenants =new ArrayList<>();
}
