package com.abc.tool_rental.service;

import com.abc.tool_rental.dto.CheckoutResponse;
import com.abc.tool_rental.exception.ErrorCode;
import com.abc.tool_rental.exception.RequestPayloadException;
import com.abc.tool_rental.model.Tool;
import com.abc.tool_rental.model.ToolType;
import com.abc.tool_rental.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckoutWithValidData() {
        String toolCode = "LADW";
        String toolTypeStr = "Ladder";
        int rentalDayCount = 3;
        int discountPercent = 10;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);
        Tool tool = new Tool(toolCode,toolTypeStr ,"Werner", toolType);

        when(toolRepository.findByToolCode(toolCode)).thenReturn(tool);

        CheckoutResponse response = checkoutService.checkout(toolCode, rentalDayCount, discountPercent, checkoutDate);

        assertNotNull(response);
        assertEquals(toolCode, response.getToolCode());
        assertEquals(toolTypeStr, response.getToolType());
        assertEquals("Werner", response.getToolBrand());
        assertEquals(rentalDayCount, response.getRentalDays());
        assertEquals(checkoutDate, response.getCheckoutDate());
        assertEquals(checkoutDate.plusDays(rentalDayCount - 1), response.getDueDate());
        assertEquals(toolType.getDailyCharge(), response.getDailyRentalCharge());
        assertEquals(BigDecimal.valueOf(3.98), response.getPreDiscountCharge());
        assertEquals(BigDecimal.valueOf(0.398), response.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.582), response.getFinalCharge());

        verify(toolRepository, times(1)).findByToolCode(toolCode);
    }

    @Test
    void testCheckoutWithToolNotFound() {
        when(toolRepository.findByToolCode("LADW")).thenReturn(null);

        RequestPayloadException exception = assertThrows(RequestPayloadException.class, () -> {
            checkoutService.checkout("LADW", 3, 10, LocalDate.of(2023, 7, 2));
        });

        assertEquals(ErrorCode.TOOL_NOT_FOUND_ERROR.getCode(), exception.getErrorCode());
    }
}
