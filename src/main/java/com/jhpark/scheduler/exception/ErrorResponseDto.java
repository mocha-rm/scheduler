package com.jhpark.scheduler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {
    private HttpStatus httpStatus;
    private String message;

    public ErrorResponseDto(HttpStatus status, String msg) {
        this.httpStatus = status;
        this.message = msg;
    }
}
