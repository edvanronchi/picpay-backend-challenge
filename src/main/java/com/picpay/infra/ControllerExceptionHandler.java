package com.picpay.infra;

import com.picpay.adapters.dtos.ExceptionDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity duplicate(DataIntegrityViolationException exception) {
        ExceptionDto exceptionDto = new ExceptionDto("Teste", 500);
        return ResponseEntity.badRequest().body(exceptionDto);
    }
}
