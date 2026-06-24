package com.ecommerce.order.management.infrastructure.adapters.inbound.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.management.application.dto.OrderSearchResponseDTO;
import com.ecommerce.order.management.application.usecase.FuzzySearchOrdersUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/search")
@Tag(name = "Order Search", description = "Fuzzy search engine for orders and items")
public class OrderSearchController {

    private final FuzzySearchOrdersUseCase fuzzySearchOrdersUseCase;

    public OrderSearchController(FuzzySearchOrdersUseCase fuzzySearchOrdersUseCase) {
        this.fuzzySearchOrdersUseCase = fuzzySearchOrdersUseCase;
    }

    @GetMapping("/orders")
    @Operation(summary = "Search orders using fuzzy matching (ignores accents, case, and minor typos)")
    public ResponseEntity<List<OrderSearchResponseDTO>> searchOrders(
            @RequestParam(name = "q", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok(fuzzySearchOrdersUseCase.execute(keyword));
    }
}