package com.claim_processor.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.claim_processor.dto.ClaimDTO;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, ClaimDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, ClaimDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceClaimEvent(String topic, ClaimDTO dto) {
        kafkaTemplate.send(topic, dto);
    }
}
