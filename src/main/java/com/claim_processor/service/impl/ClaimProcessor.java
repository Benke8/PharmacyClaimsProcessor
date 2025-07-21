package com.claim_processor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.claim_processor.dto.ClaimDTO;
import com.claim_processor.dto.ErrorClaimDTO;
import com.claim_processor.entity.ErrorClaim;
import com.claim_processor.util.ClaimMapper;

@Service
public class ClaimProcessor {

    @Autowired
    private KieContainer kieContainer;

    @Value("${claim.retry-count}")
    private int retryCount;
    
    private final ClaimServiceImpl claimService;
    private final ErrorClaimServiceImpl errorClaimService;
    private final KafkaProducerService kafkaProducerService;
    private final ClaimMapper claimMapper;

    public ClaimProcessor(ClaimServiceImpl claimService,
                          ErrorClaimServiceImpl errorClaimService,
                          KafkaProducerService kafkaProducerService,
                          ClaimMapper claimMapper) {
        this.claimService = claimService;
        this.errorClaimService = errorClaimService;
        this.kafkaProducerService = kafkaProducerService;
        this.claimMapper = claimMapper;
    }

    @KafkaListener(topics = "live-claims", groupId = "claim-group")
    public void ProcessClaim(ClaimDTO dto) {
        System.out.println("Processing Claim: " + dto.getClaimNumber());

        List<String> errorList = new ArrayList<>();

        KieSession kieSession = kieContainer.newKieSession();

        kieSession.setGlobal("errorList", errorList);

        kieSession.insert(dto);
        kieSession.fireAllRules();
        kieSession.dispose();
        if(errorList.isEmpty()) {
            claimService.saveClaim(claimMapper.toModel(dto));
            System.out.println("Claim processed successfully");
            kafkaProducerService.produceClaimEvent("processed-claims", dto);
        }
        else {
            ErrorClaimDTO eDto = (ErrorClaimDTO) dto;
            if(eDto.getRetryCount() > 2)
            {
                System.out.println("Claim: " + eDto.getClaimNumber() + " reached maximum retry count");
            }
            else {
                eDto.setRetryCount(eDto.getRetryCount() + 1);
                errorClaimService.updateClaim((ErrorClaim)claimMapper.toModel(eDto), dto.getClaimNumber());
                System.out.println("Claim moved to error repository: " + errorList);
                kafkaProducerService.produceClaimEvent("live-claims", dto);    
            }
        }
    }
}
