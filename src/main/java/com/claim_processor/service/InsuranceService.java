package com.claim_processor.service;

import java.util.List;

import com.claim_processor.entity.Insurance;

public interface InsuranceService {
    Insurance saveInsurance(Insurance insurance);

    List<Insurance> getInsurances();

    Insurance updatInsurance(Insurance insurance, Long insuranceId);

    void deleteInsuranceById(Long insuranceId);
}
