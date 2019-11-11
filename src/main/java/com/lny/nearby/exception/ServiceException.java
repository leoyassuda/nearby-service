package com.lny.nearby.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceException  extends ResponseStatusException {
    public ServiceException(HttpStatus status, String reason, Throwable cause) {
        //TODO: implementar

        super(status, reason, cause);

        System.out.println(">>> ServiceException : " + reason);
        System.out.println(cause.getMessage());
    }
}
