package com.safestagram.ws.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SafestagramServiceException extends RuntimeException {

    private Integer code;
    private String message;

    public SafestagramServiceException(ErrorCode errorCode) {
        super();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
