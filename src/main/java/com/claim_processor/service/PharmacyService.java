package com.claim_processor.service;

import java.util.List;

import com.claim_processor.entity.Pharmacy;

public interface PharmacyService {
    Pharmacy savePharmacy(Pharmacy pharmacy);

    List<Pharmacy> getPharmacies();

    Pharmacy updatePharmacy(Pharmacy pharmacy, Long pharmacyId);

    void deleteByPharmacyId(Long pharmacyId);
}
