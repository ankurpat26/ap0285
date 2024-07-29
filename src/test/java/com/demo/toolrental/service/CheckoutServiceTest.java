package com.demo.toolrental.service;

import com.demo.toolrental.dto.CheckoutResponse;
import com.demo.toolrental.exception.ErrorCode;
import com.demo.toolrental.exception.RequestPayloadException;
import com.demo.toolrental.model.Tool;
import com.demo.toolrental.model.ToolType;
import com.demo.toolrental.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

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

        when(toolRepository.findById(toolCode)).thenReturn(Optional.of(tool));

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

        verify(toolRepository, times(1)).findById(toolCode);
    }

    @Test
    void testCheckoutWithToolNotFound() {
        when(toolRepository.findById("LADW")).thenReturn(Optional.empty());

        RequestPayloadException exception = assertThrows(RequestPayloadException.class, () -> {
            checkoutService.checkout("LADW", 3, 10, LocalDate.of(2023, 7, 2));
        });

        assertEquals(ErrorCode.TOOL_NOT_FOUND_ERROR.getCode(), exception.getErrorCode());
    }
}
