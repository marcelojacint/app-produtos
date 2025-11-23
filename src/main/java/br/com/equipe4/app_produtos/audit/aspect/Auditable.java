package br.com.equipe4.app_produtos.audit.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String entityName();
    String action(); // create, update, delete (serve como uma anotação para marcar métodos que devem ser auditados)
}
