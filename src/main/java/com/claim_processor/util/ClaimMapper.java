package com.claim_processor.util;

import org.springframework.stereotype.Component;

import com.claim_processor.dto.ClaimDTO;
import com.claim_processor.dto.InsuranceDTO;
import com.claim_processor.dto.PatientDTO;
import com.claim_processor.dto.PharmacyDTO;
import com.claim_processor.entity.Claim;
import com.claim_processor.entity.Insurance;
import com.claim_processor.entity.Patient;
import com.claim_processor.entity.Pharmacy;
import com.claim_processor.enums.ClaimStatus;
import com.claim_processor.enums.InsureStatus;

@Component
public class ClaimMapper {
    
    public Claim toModel(ClaimDTO dto) {
        Claim claim = new Claim();
        claim.setClaimNumber(dto.getClaimNumber());
        claim.setPrescriptionNumber(dto.getPrescriptionNumber());
        claim.setNdc(dto.getNdc());
        claim.setDrugName(dto.getDrugName());
        claim.setAmountDispensed(dto.getAmountDispensed());
        claim.setFillDate(dto.getFillDate());
        claim.setFullTotal(dto.getFullTotal());
        claim.setCoveredTotal(dto.getCoveredTotal());
        claim.setPatientTotal(dto.getPatientTotal());
        claim.setDateSubmitted(dto.getDateSubmitted());

        ClaimStatus status = ClaimStatus.ERROR;
        switch (dto.getStatus().toUpperCase()) {
            case "SUBMITTED":
                status = ClaimStatus.SUBMITTED;
                break;
            case "APPROVED":
                status = ClaimStatus.APPROVED;
                break;
            case "DENIED":
                status = ClaimStatus.DENIED;
                break;
            case "PENDING":
                status = ClaimStatus.PENDING;
            default:
                break;
        }

        claim.setStatus(status);

        claim.setPatient(toPatientModel(dto.getPatient()));
        claim.setInsurance(toInsuranceModel(dto.getInsurance()));
        claim.setPharmacy(toPharmacyModel(dto.getPharmacy()));

        return claim;
    }

    private Patient toPatientModel(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setPatientId(dto.getPatientId());
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setAddress(dto.getAddress());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setEmail(dto.getEmail());

        return patient;
    }

    private Insurance toInsuranceModel(InsuranceDTO dto) {
        Insurance insurance = new Insurance();
        insurance.setNpi(dto.getNpi());
        insurance.setInsurerName(dto.getInsurerName());
        insurance.setInsurerAbv(dto.getInsurerAbv());
        insurance.setPlanName(dto.getPlanName());
        insurance.setGroupNumber(dto.getGroupNumber());
        insurance.setMemberId(dto.getMemberId());
        insurance.setCoverageStartDate(dto.getCoverageStartDate());
        insurance.setCoverageEndDate(dto.getCoverageEndDate());
        insurance.setCoinsurancePercent(dto.getCoinsurancePercent());
        insurance.setIndDeductible(dto.getIndDeductible());
        insurance.setFamDeductible(dto.getFamDeductible());
        insurance.setMaxOutOfPocket(dto.getMaxOutOfPocket());

        InsureStatus status = InsureStatus.INACTIVE;
        switch (dto.getStatus().toUpperCase()) {
            case "ACTIVE":
                status = InsureStatus.ACTIVE;
                break;
            default:
                break;
        }

        insurance.setStatus(status);

        return insurance;
    }

    private Pharmacy toPharmacyModel(PharmacyDTO dto) {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(dto.getName());
        pharmacy.setNpi(dto.getNpi());
        pharmacy.setContactName(dto.getContactName());
        pharmacy.setPhoneNumber(dto.getPhoneNumber());
        pharmacy.setEmail(dto.getEmail());
        pharmacy.setAddress(dto.getAddress());

        return pharmacy;
    }
}
