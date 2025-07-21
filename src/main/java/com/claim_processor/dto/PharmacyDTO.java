package com.claim_processor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacyDTO {
    private String npi;
    private String name;
    private String contactName;
    private String phoneNumber;
    private String email;
    private String address;
}
