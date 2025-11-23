package br.com.equipe4.app_produtos.audit.service;

import br.com.equipe4.app_produtos.model.AuditLog;
import br.com.equipe4.app_produtos.audit.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String entityName, Long entityId, String action, 
                   String oldValue, String newValue, String changedBy) { 
        AuditLog log = new AuditLog(
            entityName, 
            entityId, 
            action, 
            oldValue, 
            newValue, 
            changedBy
        );
        auditLogRepository.save(log); //salva o log no banco
    }

    public List<AuditLog> getAuditLogs(String entityName) { //busca log por entidade
        return auditLogRepository.findByEntityNameOrderByChangedAtDesc(entityName);
    }
}
