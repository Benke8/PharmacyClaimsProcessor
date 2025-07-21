package com.claim_processor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim_processor.entity.Claim;
import com.claim_processor.repository.ErrorClaimRepository;
import com.claim_processor.service.ClaimService;

@Service
public class ErrorClaimServiceImpl implements ClaimService {

    @Autowired
    private ErrorClaimRepository errorClaimRepository;

    @Override
    public Claim saveClaim(Claim claim) {
        // TODO Auto-generated method stub
        //make sure this returns errorclaim
        throw new UnsupportedOperationException("Unimplemented method 'saveClaim'");
    }

    @Override
    public List<Claim> getClaims() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClaims'");
    }

    @Override
    public Claim updateClaim(Claim claim, Long claimId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateClaim'");
    }

    @Override
    public void deleteClaimById(Long claimId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteClaimById'");
    }
    
}
