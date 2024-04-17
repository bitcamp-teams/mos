package com.mos.global.auth.exception;

public class LoginApiException extends RuntimeException{
    public LoginApiException(String message) {
        super(message);
    }
}
