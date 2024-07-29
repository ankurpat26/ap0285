package com.abc.tool_rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static com.abc.tool_rental.exception.ErrorCode.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestPayloadException.class)
    public ResponseEntity<ErrorResponse> handleRequestPayloadException(RequestPayloadException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(Exception ex, WebRequest request) {
        if (ex.getMessage().contains(INVALID_DISCOUNT_PERCENT_FORMAT.getCode())) {
            ErrorResponse errorResponse = new ErrorResponse(INVALID_DISCOUNT_PERCENT_FORMAT.getCode(), INVALID_DISCOUNT_PERCENT_FORMAT.getDefaultMessage(), LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else if (ex.getMessage().contains(INVALID_DATE_FORMAT.getCode())) {
            ErrorResponse errorResponse = new ErrorResponse(INVALID_DATE_FORMAT.getCode(), INVALID_DATE_FORMAT.getDefaultMessage(), LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else if (ex.getMessage().contains(DISCOUNT_PERCENT_ERROR.getCode())) {
            ErrorResponse errorResponse = new ErrorResponse(DISCOUNT_PERCENT_ERROR.getCode(), DISCOUNT_PERCENT_ERROR.getDefaultMessage(), LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "Internal Server Error", LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "Internal Server Error", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
