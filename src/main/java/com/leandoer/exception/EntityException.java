package com.leandoer.exception;

public abstract class EntityException extends RuntimeException {
    public EntityException(String message) {
        super(message);
    }

    public abstract int getCode();
}
