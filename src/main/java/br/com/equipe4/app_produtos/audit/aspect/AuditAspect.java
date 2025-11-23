package br.com.equipe4.app_produtos.audit.aspect;

import br.com.equipe4.app_produtos.model.AuditLog;
import br.com.equipe4.app_produtos.audit.service.AuditLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ObjectMapper objectMapper;

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        String entityName = auditable.entityName();
        String action = auditable.action();
        
        Object[] args = joinPoint.getArgs();
        Object entity = args[0];
        Long entityId = null;
        String oldValue = null;
        
        try {
            // Se for uma atualização/deleção, captura o estado anterior
            if ("UPDATE".equals(action) || "DELETE".equals(action)) {
                entityId = (Long) entity.getClass().getMethod("getId").invoke(entity);
                Object existingEntity = joinPoint.getTarget()
                    .getClass()
                    .getMethod("findById", Long.class)
                    .invoke(joinPoint.getTarget(), entityId);
                if (existingEntity != null) {
                    oldValue = objectMapper.writeValueAsString(existingEntity);
                }
            }
            
            // executa o método original
            Object result = joinPoint.proceed();
            
            // Se for uma criação ou atualização, captura o novo estado
            if (result != null) {
                String newValue = "DELETE".equals(action) ? null : objectMapper.writeValueAsString(result);
                String changedBy = getCurrentUser();
                
                if (entityId == null && result != null) {
                    entityId = getEntityId(result);
                }
                
                if (entityId != null) {
                    auditLogService.log(
                        entityName,
                        entityId,
                        action,
                        oldValue,
                        newValue,
                        changedBy
                    );
                }
            }
            
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    private Long getEntityId(Object entity) throws Exception {
        try {
            return (Long) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
                return authentication.getName();
            }
        } catch (Exception e) {
            // ignora erros de autenticação
        }
        return "system";
    }
}
