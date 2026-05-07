package com.example.vault.controller;

import com.example.vault.model.AuditLog;
import com.example.vault.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@CrossOrigin(origins="*") //Allows testing it from diff ports/browsers
public class AuditController {

    private final AuditLogService auditLogService;

    /*
    * Get all logs stored  in the vault
    * URL : GET https:// localhost:8081/api/audit/logs
     */

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLog>> getAllLogs(){
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }
    // Gets logs for a specific user or service
    @GetMapping("logs/{actorId}")
    public ResponseEntity<List<AuditLog>> getLogsByActor(@PathVariable String actorId){
        return ResponseEntity.ok(auditLogService.getLogsByActor(actorId));
    }
}
