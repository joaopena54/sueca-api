package com.example.Sueca_api.business.match.exception;

import com.example.Sueca_api.base.exceptions.ErrorCodeException;
import com.example.Sueca_api.base.exceptions.ErrorCodeList;

public class UserAlreadyInMatchException extends ErrorCodeException {

  public UserAlreadyInMatchException() {
    super(ErrorCodeList.USER_ALREADY_IN_MATCH.getErrorCode());
  }
}
