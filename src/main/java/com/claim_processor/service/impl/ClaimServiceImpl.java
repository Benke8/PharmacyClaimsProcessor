package com.claim_processor.service.impl;

import java.util.List;
import java.util.Objects;

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
        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> getClaims() {
        return (List<Claim>) claimRepository.findAll();
    }

    @Override
    public Claim updateClaim(Claim claim, Long claimId) {
        Claim clmdb = claimRepository.findById(claimId).get();

        if(Objects.nonNull(claim.getClaimNumber()) && claim.getClaimNumber() != 0) {
            clmdb.setClaimNumber(claim.getClaimNumber());
        }

        if(Objects.nonNull(claim.getPrescriptionNumber()) && !"".equalsIgnoreCase(claim.getPrescriptionNumber())) {
            clmdb.setPrescriptionNumber(claim.getPrescriptionNumber());
        }

        if(Objects.nonNull(claim.getNdc()) && !"".equalsIgnoreCase(claim.getNdc())) {
            clmdb.setNdc(claim.getNdc());
        }

        if(Objects.nonNull(claim.getDrugName()) && !"".equalsIgnoreCase(claim.getDrugName())) {
            clmdb.setDrugName(claim.getDrugName());
        }

        if(Objects.nonNull(claim.getAmountDispensed()) && claim.getAmountDispensed() != 0) {
            clmdb.setAmountDispensed(claim.getAmountDispensed());
        }

        if(Objects.nonNull(claim.getFillDate()) && !"".equalsIgnoreCase(claim.getFillDate())) {
            clmdb.setFillDate(claim.getFillDate());
        }

        if(Objects.nonNull(claim.getFullTotal()) && claim.getFullTotal() != 0) {
            clmdb.setFullTotal(claim.getFullTotal());
        }

        if(Objects.nonNull(claim.getCoveredTotal()) && claim.getCoveredTotal() != 0) {
            clmdb.setCoveredTotal(claim.getCoveredTotal());
        }

        if(Objects.nonNull(claim.getPatientTotal()) && claim.getPatientTotal() != 0) {
            clmdb.setPatientTotal(claim.getPatientTotal());
        }

        if(Objects.nonNull(claim.getDateSubmitted()) && !"".equalsIgnoreCase(claim.getDateSubmitted())) {
            clmdb.setDateSubmitted(claim.getDateSubmitted());
        }

        if(Objects.nonNull(claim.getStatus())) {
            clmdb.setStatus(claim.getStatus());
        }
        

        return claimRepository.save(clmdb);
    }

    @Override
    public void deleteClaimById(Long claimId) {
        claimRepository.deleteById(claimId);
    }
    
}
