package com.claim_processor.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {
    
    private static final KieServices kieServices = KieServices.Factory.get();
    private static final String RULES_DRL = "claim-rules.drl";
 
    @Bean
    KieContainer kieContainer() {
         KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
         kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_DRL));
         KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
         kb.buildAll();
         if(kb.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            throw new RuntimeException("Drools Build Errors:\n" + kb.getResults().toString());
         }
         KieModule kieModule = kb.getKieModule();
         KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
         return kieContainer;
     }
}
