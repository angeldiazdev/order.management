package com.ecommerce.order.management.infrastructure.adapters.outbound.responseDTO;

import lombok.Data;

@Data
public class ExternalItemResponse {
    private String itemId;
    private Integer quantity;
    private String displayName;
}