package com.abc.tool_rental.controller;

import com.abc.tool_rental.dto.CheckoutRequest;
import com.abc.tool_rental.dto.CheckoutResponse;
import com.abc.tool_rental.service.CheckoutService;
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


        CheckoutResponse response = checkoutService.checkout(request.getToolCode(), request.getRentalDayCount(), request.getDiscountPercent(), request.getCheckoutDate());
        return ResponseEntity.ok(response);


    }
}
