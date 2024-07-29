package com.abc.tool_rental.service;

import com.abc.tool_rental.dto.CheckoutResponse;
import com.abc.tool_rental.exception.ErrorCode;
import com.abc.tool_rental.exception.RequestPayloadException;
import com.abc.tool_rental.model.Tool;
import com.abc.tool_rental.model.ToolType;
import com.abc.tool_rental.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static com.abc.tool_rental.util.HolidaysUtil.getHolidays;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class CheckoutService {
    @Autowired
    private ToolRepository toolRepository;

    public CheckoutResponse checkout(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) {
        if (rentalDayCount < 1) {
            throw new RequestPayloadException(ErrorCode.RENTAL_DAY_COUNT_ERROR);
        }

        if (isBlank(toolCode)) {
            throw new RequestPayloadException(ErrorCode.TOOL_CODE_EMPTY);
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new RequestPayloadException(ErrorCode.DISCOUNT_PERCENT_ERROR);
        }

        Tool tool = toolRepository.findById(toolCode).orElseThrow(() -> new RequestPayloadException(ErrorCode.TOOL_NOT_FOUND_ERROR));
        if (tool == null) {
            throw new RequestPayloadException(ErrorCode.TOOL_NOT_FOUND_ERROR);
        }

        LocalDate dueDate = checkoutDate.plusDays(rentalDayCount - 1);
        BigDecimal dailyCharge = tool.getToolTypeDetails().getDailyCharge();
        int chargeDays = calculateChargeDays(checkoutDate, dueDate, tool.getToolTypeDetails());
        BigDecimal preDiscountCharge = dailyCharge.multiply(BigDecimal.valueOf(chargeDays));
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100));
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        return new CheckoutResponse(
                toolCode,
                tool.getToolType(),
                tool.getBrand(),
                rentalDayCount,
                checkoutDate,
                dueDate,
                dailyCharge,
                chargeDays,
                preDiscountCharge,
                discountPercent,
                discountAmount,
                finalCharge
        );
    }

    private int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, ToolType toolTypeDetails) {
        // Implement logic to calculate chargeable days
        // Consider weekday, weekend, and holiday charges based on ToolType details
        int chargeDays = 0;
        Set<LocalDate> holidays = getHolidays(checkoutDate.getYear());
        for (LocalDate date = checkoutDate; !date.isAfter(dueDate); date = date.plusDays(1)) {
            boolean isWeekday = date.getDayOfWeek().getValue() < 6;
            boolean isWeekend = !isWeekday;
            boolean isHoliday = holidays.contains(date);

            if (isHoliday && !toolTypeDetails.isHolidayCharge()) {
                continue; // Skip holidays regardless of whether they are weekdays or weekends
            }

            if ((isWeekday && toolTypeDetails.isWeekdayCharge()) ||
                    (isWeekend && toolTypeDetails.isWeekendCharge())) {
                chargeDays++;
            }
        }
        return chargeDays;
    }
}
