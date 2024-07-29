package com.abc.tool_rental.dto;

import com.abc.tool_rental.util.DateDeserializer;
import com.abc.tool_rental.util.PercentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CheckoutRequest {

    private String toolCode;
    private int rentalDayCount;

    @JsonDeserialize(using = PercentDeserializer.class)
    private int discountPercent;

    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate checkoutDate;

}
