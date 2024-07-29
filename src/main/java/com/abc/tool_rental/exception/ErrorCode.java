package com.abc.tool_rental.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    RENTAL_DAY_COUNT_ERROR("RENTAL_DAY_COUNT_ERROR", "Rental day count must be 1 or greater."),
    TOOL_CODE_EMPTY("TOOL_CODE_EMPTY", "Tool Code can not be empty or null"),
    DISCOUNT_PERCENT_ERROR("DISCOUNT_PERCENT_ERROR", "Discount percent must be between 0 and 100."),
    TOOL_NOT_FOUND_ERROR("TOOL_NOT_FOUND_ERROR", "Tool not found."),
    INVALID_DATE_FORMAT("INVALID_DATE_FORMAT", "Date must be in format of MM/DD/YY"),
    INVALID_DISCOUNT_PERCENT_FORMAT("INVALID_DISCOUNT_PERCENT_FORMAT", "Discount Percent is Not valid, must be between a whole Number and % should be followed by num ");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

}
