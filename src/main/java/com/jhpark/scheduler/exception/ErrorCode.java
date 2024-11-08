package com.jhpark.scheduler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    INVALID_REQUEST(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다"),
    DELETED_SCHEDULE(HttpStatus.NOT_FOUND, "삭제된 일정입니다");

    private final HttpStatus httpStatus;
    private final String message;
}
