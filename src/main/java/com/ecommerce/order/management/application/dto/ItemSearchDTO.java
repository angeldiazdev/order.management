package com.ecommerce.order.management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchDTO {
    private String itemId;
    private String displayName;
    private Integer quantity;
}