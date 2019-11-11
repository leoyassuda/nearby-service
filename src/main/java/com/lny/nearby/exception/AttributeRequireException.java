package com.lny.nearby.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AttributeRequireException extends ResponseStatusException {
    public AttributeRequireException(HttpStatus status, String message, Throwable e) {
        super(status, message, e);
    }
}
