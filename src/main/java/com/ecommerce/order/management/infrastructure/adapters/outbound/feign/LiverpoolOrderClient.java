package com.ecommerce.order.management.infrastructure.adapters.outbound.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.order.management.infrastructure.adapters.outbound.responseDTO.ExternalOrderResponse;

@FeignClient(name = "liverpoolOrderClient", url = "${liverpool.api.orders.url}")
public interface LiverpoolOrderClient {
    @GetMapping
    List<ExternalOrderResponse> getOrders();
}