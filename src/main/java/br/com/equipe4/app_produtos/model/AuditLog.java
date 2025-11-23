package br.com.equipe4.app_produtos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityName; // nome da entidade alterada

    @Column(nullable = false)
    private Long entityId; // id da entidade alterada

    @Column(nullable = false)
    private String action; // create, update, delete

    @Column(columnDefinition = "TEXT")
    private String oldValue; // estado anterior

    @Column(columnDefinition = "TEXT")
    private String newValue; // estado atual

    @Column(nullable = false)
    private String changedBy; // quem fez a alteração

    @Column(nullable = false, updatable = false)
    private LocalDateTime changedAt = LocalDateTime.now(); // quando foi alterado

    public AuditLog(String entityName, Long entityId, String action, 
                   String oldValue, String newValue, String changedBy) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changedBy = changedBy;
    }
}
