package com.slippery.rentalmanagementsystem.repository;

import com.slippery.rentalmanagementsystem.model.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalAgreementRepository extends JpaRepository<RentalAgreement,Long> {
}
