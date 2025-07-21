package com.claim_processor.service;

import java.util.List;

import com.claim_processor.entity.Patient;

public interface PatientService {
    Patient savePatient(Patient patient);

    List<Patient> getPatients();

    Patient updatePatient(Patient patient, Long patientId);

    void deletePatientById(Long patientId);
}
