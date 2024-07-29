package com.demo.toolrental.dto;

import com.demo.toolrental.util.CurrencySerializer;
import com.demo.toolrental.util.DateSerializer;
import com.demo.toolrental.util.PercentSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CheckoutResponse {
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;

    @JsonSerialize(using = DateSerializer.class)
    private LocalDate checkoutDate;

    @JsonSerialize(using = DateSerializer.class)
    private LocalDate dueDate;

    @JsonSerialize(using = CurrencySerializer.class)
    private BigDecimal dailyRentalCharge;
    private int chargeDays;

    @JsonSerialize(using = CurrencySerializer.class)
    private BigDecimal preDiscountCharge;

    @JsonSerialize(using = PercentSerializer.class)
    private int discountPercent;

    @JsonSerialize(using = CurrencySerializer.class)
    private BigDecimal discountAmount;

    @JsonSerialize(using = CurrencySerializer.class)
    private BigDecimal finalCharge;
}