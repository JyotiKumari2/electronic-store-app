package com.lcwd.electronic_store.exceptions;

public class BadApiRequestException extends RuntimeException {

    public BadApiRequestException(String message)
    {
        super(message);
    }
    public BadApiRequestException() {
        super("Bad Request !!");
    }

}
