package com.claim_processor.service;

import com.claim_processor.entity.ErrorClaim;

import java.util.List;

public interface ErrorClaimService {
    ErrorClaim saveClaim(ErrorClaim claim);

    List<ErrorClaim> getClaims();

    ErrorClaim updateClaim(ErrorClaim claim, Long claimId);

    void deleteClaimById(Long claimId);
}
