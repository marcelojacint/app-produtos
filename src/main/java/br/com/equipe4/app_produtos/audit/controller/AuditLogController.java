package br.com.equipe4.app_produtos.audit.controller;

import br.com.equipe4.app_produtos.model.AuditLog;
import br.com.equipe4.app_produtos.audit.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAuditLogs(
            @RequestParam(required = true) String entity) {
        return ResponseEntity.ok(auditLogService.getAuditLogs(entity));
    }
}
