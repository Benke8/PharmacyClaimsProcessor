package com.claim_processor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.claim_processor.entity.Insurance;

@Repository
public interface InsuranceRepository extends CrudRepository<Insurance, Long>{
    
}
