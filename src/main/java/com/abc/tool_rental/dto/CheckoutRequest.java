package com.abc.tool_rental.dto;

import com.abc.tool_rental.util.DateDeserializer;
import com.abc.tool_rental.util.PercentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

public class CheckoutRequest {

    private String toolCode;
    private int rentalDayCount;
    @JsonDeserialize(using = PercentDeserializer.class)
    private int discountPercent;

    @JsonDeserialize(using = DateDeserializer.class)

    private LocalDate checkoutDate;

    // Getters and Setters
    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public int getRentalDayCount() {
        return rentalDayCount;
    }

    public void setRentalDayCount(int rentalDayCount) {
        this.rentalDayCount = rentalDayCount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
