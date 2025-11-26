package com.example.Sueca_api.base.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeList {
  INVALID_USER(1, "Invalid user", NOT_FOUND),
  USER_ALREADY_IN_MATCH(2, "User already in match", BAD_REQUEST),
  ;

  private final ErrorCode errorCode;

  ErrorCodeList(int code, String errorMessage, HttpStatus httpStatus) {
    errorCode = new ErrorCode(code, errorMessage, httpStatus);
  }
}
