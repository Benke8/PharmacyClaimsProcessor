package com.claim_processor.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ErrorClaim extends Claim {
    private int retryCount;
}
