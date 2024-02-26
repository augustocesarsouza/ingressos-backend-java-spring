package com.backend.ingresso.domain.validations;

public class DomainValidationException extends Exception {
    public DomainValidationException(String error) {
        super(error);
    }

    public static void when(boolean hasError, String message) throws DomainValidationException{
        if(hasError)
            throw new DomainValidationException(message);
    }
}
