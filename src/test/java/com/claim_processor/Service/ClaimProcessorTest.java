package com.claim_processor.Service;

import com.claim_processor.dto.ClaimDTO;
import com.claim_processor.dto.ErrorClaimDTO;
import com.claim_processor.entity.Claim;
import com.claim_processor.entity.ErrorClaim;
import com.claim_processor.util.ClaimMapper;
import com.claim_processor.repository.ClaimRepository;
import com.claim_processor.repository.ErrorClaimRepository;
import com.claim_processor.service.impl.ClaimProcessor;
import com.claim_processor.service.impl.ClaimServiceImpl;
import com.claim_processor.service.impl.ErrorClaimServiceImpl;
import com.claim_processor.service.impl.KafkaProducerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ClaimProcessorTest {

    private ClaimServiceImpl claimServiceImpl;
    private ErrorClaimServiceImpl errorClaimServiceImpl;
    private KafkaProducerService kafkaProducerService;
    private ClaimMapper claimMapper;
    private KieSession kieSession;
    private ClaimProcessor claimProcessor;
    private KieContainer kieContainer;

    @BeforeEach
    void setUp() {
        claimServiceImpl = mock(ClaimServiceImpl.class);
        errorClaimServiceImpl = mock(ErrorClaimServiceImpl.class);
        kafkaProducerService = mock(KafkaProducerService.class);
        claimMapper = mock(ClaimMapper.class);
        kieContainer = mock(KieContainer.class);
        kieSession = mock(KieSession.class);

        when(kieContainer.newKieSession()).thenReturn(kieSession);

        claimProcessor = new ClaimProcessor(
                claimServiceImpl,
                errorClaimServiceImpl,
                kafkaProducerService,
                claimMapper,
                kieContainer
        );
    }

    /*
     * This method simulates workflow with a valid claim
     */
    @Test
    void testProcessValidClaim_savesToClaimRepo_andSendsToProcessedClaimsTopic() {
    
        ClaimDTO dto = new ClaimDTO();
        dto.setClaimNumber(Long.valueOf(4444));
        Claim mappedClaim = new Claim();
        when(claimMapper.toModel(dto)).thenReturn(mappedClaim);

        
        doAnswer(invocation -> {
            Object globalValue = invocation.getArgument(1);
            if (globalValue instanceof List) {
                List<String> errorList = (List<String>) globalValue;
                errorList.clear();
             }
            return null;
        }).when(kieSession).setGlobal(eq("errorList"), any());


        
        claimProcessor.processClaim(dto);

        verify(claimServiceImpl).saveClaim(mappedClaim);
        verify(errorClaimServiceImpl, never()).saveClaim(any());
        verify(kafkaProducerService).produceClaimEvent("processed-claims", dto);
    }

    /*
     * This method details workflow with an errored claim
     */
    @Test
    void testProcessInvalidClaim_savesToErrorRepo_andRetriesClaim() {
    
    ClaimDTO dto = new ClaimDTO();
    dto.setClaimNumber(Long.valueOf(5555));
    dto.setStatus("ERROR");

    ErrorClaim mappedErrorClaim = new ErrorClaim();
    mappedErrorClaim.setClaimNumber(dto.getClaimNumber());
    mappedErrorClaim.setRetryCount(1);

    when(claimMapper.toModel(eq(dto))).thenReturn(mappedErrorClaim);

    when(claimMapper.toModel(any(ErrorClaimDTO.class))).thenReturn(mappedErrorClaim);

    doAnswer(invocation -> {
        Object globalValue = invocation.getArgument(1);
        if (globalValue instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> errorList = (List<String>) globalValue;
            errorList.add("Simulated test error");
        }
        return null;
    }).when(kieSession).setGlobal(eq("errorList"), any());

    //doNothing().when(kieSession).fireAllRules();

    claimProcessor.processClaim(dto);

    verify(errorClaimServiceImpl).saveClaim(mappedErrorClaim);
    verify(claimServiceImpl, never()).saveClaim(any());
    verify(kafkaProducerService).produceClaimEvent("live-claims", dto);
}

}
