package com.leandoer.exception;

public class EntityConflictException extends RuntimeException {

    public EntityConflictException() {
    }

    public EntityConflictException(String message) {
        super(message);
    }
}
