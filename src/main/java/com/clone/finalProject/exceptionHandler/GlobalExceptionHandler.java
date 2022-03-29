package com.clone.finalProject.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// global 예외처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException e) {

        ErrorResponse errorResult = new ErrorResponse();

        errorResult.setHttpStatus(e.getErrorCode().getHttpStatus());
        errorResult.setStatus(e.getErrorCode().getStatus());
        errorResult.setMessage(e.getErrorCode().getMessage());

        return new ResponseEntity(errorResult, errorResult.getHttpStatus());
    }
}