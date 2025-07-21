package com.claim_processor.entity;

import java.io.Serializable;
import java.util.List;

import com.claim_processor.enums.InsureStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Insurance implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, name = "npi", nullable = false)
    private String npi;

    @Column(nullable = false)
    private String insurerName;

    private String insurerAbv;
    private String planName;
    private String groupNumber;
    private String memberId;
    private String coverageStartDate;
    private String coverageEndDate;
    private int coinsurancePercent;
    private double indDeductible;
    private double famDeductible;
    private double maxOutOfPocket;

    @Column(nullable = false)
    private InsureStatus status;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Claim> claims;
}
