package com.claim_processor.service;

import com.claim_processor.entity.Claim;

import java.util.List;

public interface ClaimService {
    Claim saveClaim(Claim claim);

    List<Claim> getClaims();

    Claim updateClaim(Claim claim, Long claimId);

    void deleteClaimById(Long claimId);
}
