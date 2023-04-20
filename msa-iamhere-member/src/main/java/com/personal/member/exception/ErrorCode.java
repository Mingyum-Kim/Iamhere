package com.personal.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    MAIL_DUPLICATED(HttpStatus.CONFLICT, " ");

    private HttpStatus httpStatus;
    private String message;
}
