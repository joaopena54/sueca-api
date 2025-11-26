package com.example.Sueca_api.base.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public record ErrorCode(int code, String message, @JsonIgnore HttpStatus status) {}
