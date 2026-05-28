package com.relatandopapel.Exception;

public class BadOrderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadOrderException(String message) {
        super(message);
    }

    public BadOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
