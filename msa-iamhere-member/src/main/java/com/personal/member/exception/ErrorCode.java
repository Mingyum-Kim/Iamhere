package com.personal.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorCode {
    MAIL_DUPLICATED(HttpStatus.CONFLICT,  "The mail is already exist."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,  "No matching member found."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password does not match.");

    private HttpStatus httpStatus;
    private String message;


}
