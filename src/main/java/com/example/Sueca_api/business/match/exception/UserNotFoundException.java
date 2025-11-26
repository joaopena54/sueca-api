package com.example.Sueca_api.business.match.exception;

import com.example.Sueca_api.base.exceptions.ErrorCodeException;
import com.example.Sueca_api.base.exceptions.ErrorCodeList;

public class UserNotFoundException extends ErrorCodeException {
  public UserNotFoundException() {
    super(ErrorCodeList.INVALID_USER.getErrorCode());
  }
}
