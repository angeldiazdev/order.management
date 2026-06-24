package com.ecommerce.order.management.infrastructure.adapters.outbound.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.order.management.infrastructure.adapters.outbound.responseDTO.ExternalItemResponse;

@FeignClient(name = "liverpoolItemClient", url = "${liverpool.api.items.url}")
public interface LiverpoolItemClient {
    @GetMapping
    List<ExternalItemResponse> getItems();
}