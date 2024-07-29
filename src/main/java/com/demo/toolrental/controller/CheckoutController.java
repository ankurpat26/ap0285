package com.demo.toolrental.controller;

import com.demo.toolrental.dto.CheckoutRequest;
import com.demo.toolrental.dto.CheckoutResponse;
import com.demo.toolrental.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(
            @RequestBody CheckoutRequest request) {

        try {
            CheckoutResponse response = checkoutService.checkout(request.getToolCode(), request.getRentalDayCount(), request.getDiscountPercent(), request.getCheckoutDate());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw e; // Handled by GlobalExceptionHandler
        }

    }
}
