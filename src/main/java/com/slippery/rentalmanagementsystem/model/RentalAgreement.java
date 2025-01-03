package com.slippery.rentalmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startOn;
    private LocalDateTime endDate;
    private Long monthlyRent;
    private Long securityDeposit;
    private String status;
    @ManyToOne(cascade = CascadeType.ALL)
    private PropertyListing propertyListing;
    @OneToOne(cascade = CascadeType.ALL)
    private User tenant;
    @ManyToOne(cascade = CascadeType.ALL)

    private User landlord;


}
