package com.leandoer.exception;

import lombok.Getter;

@Getter
public class EntityConflictException extends EntityException {
    final int code = 101;

    public EntityConflictException(String message) {
        super(message);
    }


}
