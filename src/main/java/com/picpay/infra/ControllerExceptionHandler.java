package com.picpay.infra;

import com.picpay.application.dtos.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler()
    public ResponseEntity threatGeneralException(Exception exception) {
        logger.error("Exception:", exception);

        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.badRequest().body(exceptionDto);
    }
}
