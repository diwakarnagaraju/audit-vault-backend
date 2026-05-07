package com.example.vault.consumer;

import com.example.vault.model.AuditLog;
import com.example.vault.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuditConsumer {
    private final AuditLogService auditLogService;

    /*
    * This method listens to the "audit-topic"
    * when a message arrives, spring automatically converts it into an AuditLog object.
     */
    @KafkaListener(topics = "audit-topic", groupId = "vault-group")
    public void consume(AuditLog logData){
        log.info("#### -> Consumed message -> {}", logData);

        try{
            auditLogService.saveAuditLog(logData);
            log.info("Successfully processed log for action: {}", logData.getAction());
        } catch (Exception e){
            log.error("Error processing log from kafka: {}", e.getMessage());
        }
    }

}
