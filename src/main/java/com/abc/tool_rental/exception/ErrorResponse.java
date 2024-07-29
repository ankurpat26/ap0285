package com.abc.tool_rental.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;

    public ErrorResponse(String errorCode, String errorMessage, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

}
