package com.claim_processor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.claim_processor.entity.ErrorClaim;

@Repository
public interface ErrorClaimRepository extends CrudRepository<ErrorClaim, Long> {
    
}
