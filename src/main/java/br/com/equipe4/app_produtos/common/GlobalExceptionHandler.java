package br.com.equipe4.app_produtos.common;

import br.com.equipe4.app_produtos.infra.exceptions.EmptyOrderException;
import br.com.equipe4.app_produtos.infra.exceptions.EntityNotFoundException;
import br.com.equipe4.app_produtos.service.dto.errorDto.ValidationError;
import br.com.equipe4.app_produtos.service.dto.errorDto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("erro de validação: {} ", e.getMessage());

        List<FieldError> fieldErrors = e.getFieldErrors();

        List<ValidationError> listaErros = fieldErrors
                .stream()
                .map(fie -> new ValidationError(fie.getField(), fie.getDefaultMessage())).toList();

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), " erro de validação", listaErros);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerEntityNotFoundException(EntityNotFoundException e) {
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(EmptyOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerEmptyOrderException(EmptyOrderException e) {
        return ErrorResponse.conflict(e.getMessage());
    }
}
