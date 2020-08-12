package com.leandoer.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends EntityException {
    final int code = 102;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
