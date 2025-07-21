package com.claim_processor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceDTO {
    private String npi;
    private String insurerName;
    private String insurerAbv;
    private String groupNumber;
    private String planName;
    private String memberId;
    private String coverageStartDate;
    private String coverageEndDate;
    private int coinsurancePercent;
    private double indDeductible;
    private double famDeductible;
    private double maxOutOfPocket;
    private String status;
}
