package com.claim_processor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claim_processor.dto.ClaimDTO;
import com.claim_processor.service.impl.KafkaProducerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/claims")
public class ClaimController {
    
    private final KafkaProducerService kafkaProducerService;

    public ClaimController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping
    public ResponseEntity<String> submitClaim(@RequestBody ClaimDTO dto) {
        kafkaProducerService.produceClaimEvent("live-claims", dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Claim submitted.");
    }
    
}
