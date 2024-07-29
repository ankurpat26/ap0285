package com.demo.toolrental.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PercentSerializer extends StdSerializer<Integer> {

    public PercentSerializer() {
        this(null);
    }

    public PercentSerializer(Class<Integer> t) {
        super(t);
    }

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(String.format("%d%%", value));
    }
}
