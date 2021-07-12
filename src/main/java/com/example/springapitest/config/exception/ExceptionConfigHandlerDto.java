package com.example.springapitest.config.exception;

public class ExceptionConfigHandlerDto {
    private String field;
    private String message;

    public ExceptionConfigHandlerDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
