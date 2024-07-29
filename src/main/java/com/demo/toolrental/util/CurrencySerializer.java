package com.demo.toolrental.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CurrencySerializer extends StdSerializer<BigDecimal> {

    private static final DecimalFormat formatter = new DecimalFormat("$#,##0.00");

    public CurrencySerializer() {
        this(null);
    }

    public CurrencySerializer(Class<BigDecimal> t) {
        super(t);
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(formatter.format(value));
    }
}
