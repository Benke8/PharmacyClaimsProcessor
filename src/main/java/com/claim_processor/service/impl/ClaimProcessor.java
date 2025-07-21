package com.claim_processor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.BeanUtils;
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

    @Value("${claim.retry-count}")
    private int retryCount;
    
    private final ClaimServiceImpl claimService;
    private final ErrorClaimServiceImpl errorClaimService;
    private final KafkaProducerService kafkaProducerService;
    private final ClaimMapper claimMapper;
    private final KieContainer kieContainer;

    public ClaimProcessor(ClaimServiceImpl claimService,
                          ErrorClaimServiceImpl errorClaimService,
                          KafkaProducerService kafkaProducerService,
                          ClaimMapper claimMapper,
                          KieContainer kieContainer) {
        this.claimService = claimService;
        this.errorClaimService = errorClaimService;
        this.kafkaProducerService = kafkaProducerService;
        this.claimMapper = claimMapper;
        this.kieContainer = kieContainer;
    }

    @KafkaListener(topics = "live-claims", groupId = "claim-group")
    public void processClaim(ClaimDTO dto) {
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
            ErrorClaimDTO eDto = new ErrorClaimDTO();
            BeanUtils.copyProperties(dto, eDto);

            if(eDto.getRetryCount() > 2)
            {
                System.out.println("Claim: " + eDto.getClaimNumber() + " reached maximum retry count");
            }
            else {
                eDto.setRetryCount(eDto.getRetryCount() + 1);
                ErrorClaim eClaim = claimMapper.toModel(eDto);
                errorClaimService.saveClaim(eClaim);
                System.out.println("Claim moved to error repository: " + errorList + "\nFor " + eDto.getRetryCount() + " time");
                kafkaProducerService.produceClaimEvent("live-claims", dto);    
            }
        }
    }
}
