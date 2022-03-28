package com.clone.finalProject.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// global 예외처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(ex.getMessage());

        return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleApiNullPointerException(NullPointerException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(ex.getMessage());

        return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MessagingException.class})
    public ResponseEntity<Object> handleApiMessageHandlingException(MessagingException ex) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage("Missing header 'Authorization");

        return new ResponseEntity(restApiException, HttpStatus.BAD_REQUEST);
    }



}
