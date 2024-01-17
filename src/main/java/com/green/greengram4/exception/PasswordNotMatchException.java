package com.green.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordNotMatchException extends RuntimeException{
    private final ErrorCode errorCode;
}
