package br.com.equipe4.app_produtos.audit.repository;

import br.com.equipe4.app_produtos.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityNameOrderByChangedAtDesc(String entityName);
}
