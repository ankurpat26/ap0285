//package com.abc.tool_rental.util;
//
//import com.abc.tool_rental.exception.ErrorCode;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//
//public class DateDeserializer extends StdDeserializer<LocalDate> {
//
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
//
//    public DateDeserializer() {
//        this(null);
//    }
//
//    public DateDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        try {
//            return LocalDate.parse(p.getText(), formatter);
//        } catch (Exception e){
//            throw new IllegalArgumentException(e.getMessage() + " " +ErrorCode.INVALID_DATE_FORMAT.getCode());
//        }
//    }
//
//
//

package com.abc.tool_rental.util;

import com.abc.tool_rental.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateDeserializer extends StdDeserializer<LocalDate> {

    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ofPattern("M/d/yy"),
            DateTimeFormatter.ofPattern("MM/dd/yy"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy")
    };

    public DateDeserializer() {
        this(null);
    }

    public DateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateStr = p.getText();
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(e.getMessage() + " " + ErrorCode.INVALID_DATE_FORMAT.getCode());

            }
        }
        throw new IllegalArgumentException("Invalid date format: " + dateStr + " " + ErrorCode.INVALID_DATE_FORMAT.getCode());
    }
}


