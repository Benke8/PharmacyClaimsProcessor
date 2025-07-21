package com.claim_processor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.claim_processor.entity.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>{
    
}
