package com.example.Sueca_api.base.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorCodeExceptionMapper {

  @ExceptionHandler(ErrorCodeException.class)
  public ResponseEntity<ErrorCode> handleErrorCodeException(ErrorCodeException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    HttpStatus httpStatus =
        errorCode.status() != null ? errorCode.status() : HttpStatus.INTERNAL_SERVER_ERROR;
    return ResponseEntity.status(httpStatus).body(errorCode);
  }
}
