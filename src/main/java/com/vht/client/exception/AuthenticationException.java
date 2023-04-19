package com.vht.client.exception;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 8252436100241953L;

    public AuthenticationException(String message) {
        super(message);
    }
}
