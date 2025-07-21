package com.claim_processor.entity;

import java.io.Serializable;
import com.claim_processor.enums.ClaimStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Claim implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long Id;

    @Column(unique = true)
    private Long claimNumber;

    @Column(nullable = false)
    private String prescriptionNumber;

    @Column(nullable = false)
    private String ndc;

    @Column(nullable = false)
    private String drugName;

    @Column(nullable = false)
    private int amountDispensed;

    @Column(nullable = false)
    private String fillDate;

    @Column(nullable = false)
    private double fullTotal;
    private double coveredTotal;
    private double patientTotal;

    @Column(nullable = false)
    private String dateSubmitted;

    @Column(nullable = false)
    private ClaimStatus status;

    @ManyToOne
    @JoinColumn(name="insurance_id", referencedColumnName = "npi")
    private Insurance insurance;

    @ManyToOne
    @JoinColumn(name="patient_id", referencedColumnName = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="pharmacy_id", referencedColumnName = "npi")
    private Pharmacy pharmacy;
}
