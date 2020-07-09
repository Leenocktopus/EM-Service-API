package com.leandoer.exception;

public class IllegalEntityException extends RuntimeException{

    public IllegalEntityException() {
    }

    public IllegalEntityException(String message) {
        super(message);
    }
}
