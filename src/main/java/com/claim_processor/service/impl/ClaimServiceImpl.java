package com.claim_processor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim_processor.entity.Claim;
import com.claim_processor.repository.ClaimRepository;
import com.claim_processor.service.ClaimService;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public Claim saveClaim(Claim claim) {
        // TODO Auto-generated method stub
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
