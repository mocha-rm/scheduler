package com.jhpark.scheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends Exception {
    private final ErrorCode errorCode;
}
