package com.kontekapp.housingloan.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class KontekExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Provided wrong type of loan";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
