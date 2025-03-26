package com.jihun.springsecuritystudyproject.global.security.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityException extends AuthenticationException {
    public SecurityException(String message) {
        super(message);
    }
}
