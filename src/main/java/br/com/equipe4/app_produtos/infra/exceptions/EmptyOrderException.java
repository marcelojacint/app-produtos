package br.com.equipe4.app_produtos.infra.exceptions;

public class EmptyOrderException extends RuntimeException {

    public EmptyOrderException(String message) {
        super(message);
    }

    public EmptyOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
