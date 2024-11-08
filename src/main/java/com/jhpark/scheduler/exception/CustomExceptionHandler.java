package com.jhpark.scheduler.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(new ErrorResponseDto(exception.getErrorCode().getHttpStatus(), exception.getErrorCode().getMessage()));
    }
}
