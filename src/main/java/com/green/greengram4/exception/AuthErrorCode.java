package com.green.greengram4.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    VALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해 주세요."),
    NEED_SIGNIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "refresh_token 이 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;


}
