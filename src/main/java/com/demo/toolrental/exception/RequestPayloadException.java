package com.demo.toolrental.exception;

public class RequestPayloadException extends RuntimeException {
    private final ErrorCode errorCode;

    public RequestPayloadException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public RequestPayloadException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }
}
