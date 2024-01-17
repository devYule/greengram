package com.green.greengram4.exception;

import com.green.greengram4.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handle(IllegalArgumentException e) {
        log.warn("IllegalArgumentException", e);

        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER);

    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(Exception e) {
        log.warn("Exception", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler
//    public ResponseEntity<Object> handle(RestApiException e) {
//        log.warn("RestApiException", e);
//        return handleExceptionInternal(CommonErrorCode.)
//    }


    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(message == null ? makeErrorResponse(errorCode) : makeErrorResponse(errorCode, message));
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return makeErrorResponse(errorCode);
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

}
