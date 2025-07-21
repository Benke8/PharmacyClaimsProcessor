package com.claim_processor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorClaimDTO extends ClaimDTO {
    private int retryCount;
}
