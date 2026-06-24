package com.ecommerce.order.management.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderRef;
    private String orderStatus;
    private String storeName;
    private String userId;
    private String salesChannel;
    private List<Item> items;
}