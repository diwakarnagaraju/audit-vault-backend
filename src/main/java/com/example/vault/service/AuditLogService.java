package com.example.vault.service;

import com.example.vault.model.AuditLog;
import com.example.vault.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditRepository auditRepository;

    /*
    * saves a log received from kafka into Elasticsearch.
    * we add a timestamp here to ensure we know exactly when the vault received it.
     */
    public void saveAuditLog(AuditLog logData){
        if(logData.getTimeStamp() == null){
            logData.setTimeStamp(LocalDateTime.now());
        }
        log.info("saving audit log for action: {}", logData.getAction());
        auditRepository.save(logData);
    }

    /*
    Fetched all logs for the Dashboard
     */
    public List<AuditLog> getAllLogs(){
        return StreamSupport.stream(auditRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /*
    search logs by user/Service ID.
     */
    public List<AuditLog> getLogsByActor(String actorId){
        return auditRepository.findByActorId(actorId);
    }
}
