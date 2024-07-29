package com.demo.toolrental.util;

import com.demo.toolrental.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class PercentDeserializer extends StdDeserializer<Integer> {

    public PercentDeserializer() {
        this(null);
    }

    public PercentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String percentString = p.getText().trim();

        if (percentString.endsWith("%")) {
            percentString = percentString.substring(0, percentString.length() - 1);
        }

        try {
            // Check if value is a valid integer
            int discountPercent = Integer.parseInt(percentString);
            if (discountPercent < 0 || discountPercent > 100) {
                throw new IllegalArgumentException(ErrorCode.DISCOUNT_PERCENT_ERROR.getCode());

            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getCause() + " " + ErrorCode.INVALID_DISCOUNT_PERCENT_FORMAT.getCode());
        }
        if (percentString.endsWith("%")) {
            percentString = percentString.substring(0, percentString.length() - 1);
        }
        return Integer.parseInt(percentString);
    }
}
