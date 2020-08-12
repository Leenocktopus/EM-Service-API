package com.leandoer.exception;

import lombok.Getter;

@Getter
public class IllegalEntityException extends EntityException {
    final int code = 103;

    public IllegalEntityException(String message) {
        super(message);
    }
}
