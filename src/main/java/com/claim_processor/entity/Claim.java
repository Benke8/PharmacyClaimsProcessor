package com.claim_processor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Claim {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long claimId;
    private Long claimNumber;
    private String prescriptionNumber;
    private String ndc;
    private String drugName;
    private int amountDispensed;
    private LocalDate fillDate;
    private double fullTotal;
    private double coveredTotal;
    private double patientTotal;
    private String status;
    private LocalDateTime dateSubmitted;

    
}
