package com.leandoer.exception;

import lombok.Getter;

@Getter
public class IllegalEntityException extends RuntimeException {
    public IllegalEntityException(String message) {
        super(message);
    }
}
