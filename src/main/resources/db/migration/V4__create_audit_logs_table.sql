-- criação da tabela de logs de registro (create, update, delete)
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_name VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    action VARCHAR(20) NOT NULL,
    old_value TEXT,
    new_value TEXT,
    changed_by VARCHAR(100) NOT NULL,
    changed_at TIMESTAMP NOT NULL
);

-- Índice para melhorar consultas por entidade
CREATE INDEX idx_audit_logs_entity ON audit_logs (entity_name, entity_id);

-- Índice para melhorar consultas por data
CREATE INDEX idx_audit_logs_timestamp ON audit_logs (changed_at);
