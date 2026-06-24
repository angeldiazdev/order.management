package com.ecommerce.order.management.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResponseDTO {
    private String orderRef;
    private String orderStatus;
    private String storeName;
    private List<ItemSearchDTO> items;
}