package com.claim_processor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimDTO {
    private Long claimNumber;
    private String prescriptionNumber;
    private String ndc;
    private String drugName;
    private int amountDispensed;
    private String fillDate;
    private String dateSubmitted;
    private double fullTotal;
    private double coveredTotal;
    private double patientTotal;
    private String status;
    
    private InsuranceDTO insurance;
    private PatientDTO patient;
    private PharmacyDTO pharmacy;

}