package com.claim_processor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.claim_processor.entity.Pharmacy;

@Repository
public interface PharmacyRepository extends CrudRepository<Pharmacy, Long>{
    
}
