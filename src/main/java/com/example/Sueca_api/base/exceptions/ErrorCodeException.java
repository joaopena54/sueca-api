package com.example.Sueca_api.base.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorCodeException extends RuntimeException {

  private final ErrorCode errorCode;
}
