package com.claim_processor.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim_processor.entity.ErrorClaim;
import com.claim_processor.repository.ErrorClaimRepository;
import com.claim_processor.service.ErrorClaimService;

@Service
public class ErrorClaimServiceImpl implements ErrorClaimService {

    @Autowired
    private ErrorClaimRepository errorClaimRepository;

    @Override
    public ErrorClaim saveClaim(ErrorClaim claim) {
        return errorClaimRepository.save(claim);
    }
    @Override
    public List<ErrorClaim> getClaims() {
        return (List<ErrorClaim>) errorClaimRepository.findAll();
    }

    @Override
    public ErrorClaim updateClaim(ErrorClaim claim, Long claimId) {
        ErrorClaim eclmdb = errorClaimRepository.findById(claimId).get();

        if(Objects.nonNull(claim.getRetryCount()) && claim.getRetryCount() != 0) {
            eclmdb.setRetryCount(claim.getRetryCount());
        }

        return errorClaimRepository.save(eclmdb);
    }

    @Override
    public void deleteClaimById(Long claimId) {
        errorClaimRepository.deleteById(claimId);    
    }
    
}
