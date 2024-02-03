package com.green.greengram4.exception;

import com.green.greengram4.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.green.greengram4.exception.CommonErrorCode.INVALID_PARAMETER;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handle(IllegalArgumentException e) {
        log.warn("IllegalArgumentException", e);

        return handleExceptionInternal(INVALID_PARAMETER);

    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(Exception e) {
        log.warn("Exception", e);
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(RestApiException e) {
        log.warn("RestApiException", e);
        return handleExceptionInternal(e.getErrorCode(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> resolve(MethodArgumentNotValidException eBase) {
//        StringBuilder sb = new StringBuilder();
//        List<String> messages = new ArrayList<>();
//        eBase.getAllErrors().forEach(e1 -> {
//            messages.add(e1.getDefaultMessage());
//            sb.append(e1.getDefaultMessage());
//            log.warn("error message = {}", e1);
//        });
//        String errorMessage = sb.toString();

        List<String> errors = eBase.getBindingResult().getFieldErrors().stream()
                .map(o -> o.getDefaultMessage()).toList();

//        return handleExceptionInternal(INVALID_PARAMETER, errorMessage);
        return handleExceptionInternal(INVALID_PARAMETER, errors.toString());
    }

//    @ExceptionHandler
//    public ResponseEntity<Object> handle(MethodArgumentNotValidException e) {
//        return handleExceptionInternal(INVALID_PARAMETER);
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
