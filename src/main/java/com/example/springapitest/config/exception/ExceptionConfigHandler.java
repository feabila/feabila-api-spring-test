package com.example.springapitest.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionConfigHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ExceptionConfigHandlerDto> handle(MethodArgumentNotValidException exception) {
        List<ExceptionConfigHandlerDto> exceptionList = new ArrayList<>();

        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();
        fieldErros.forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ExceptionConfigHandlerDto error = new ExceptionConfigHandlerDto(err.getField(), message);
            exceptionList.add(error);
        });

        return exceptionList;
    }
}
