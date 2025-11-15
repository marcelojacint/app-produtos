package br.com.equipe4.app_produtos.common;

import br.com.equipe4.app_produtos.exceptions.EntidadeNaoEncontradaException;
import br.com.equipe4.app_produtos.service.dto.erroDto.ErroCampo;
import br.com.equipe4.app_produtos.service.dto.erroDto.ErroResposta;
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
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("erro de validação: {} ", e.getMessage());

        List<FieldError> fieldErrors = e.getFieldErrors();

        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fie -> new ErroCampo(fie.getField(), fie.getDefaultMessage())).toList();

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), " erro de validação", listaErros);

    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handlerRegistroDuplicadoException(EntidadeNaoEncontradaException e) {
        return ErroResposta.conflito(e.getMessage());
    }
}
