package com.ecommerce.order.management.infrastructure.adapters.outbound;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ecommerce.order.management.domain.model.Item;
import com.ecommerce.order.management.domain.model.Order;
import com.ecommerce.order.management.domain.repository.OrderRepository;
import com.ecommerce.order.management.infrastructure.adapters.outbound.feign.LiverpoolItemClient;
import com.ecommerce.order.management.infrastructure.adapters.outbound.feign.LiverpoolOrderClient;

@Component
public class OrderOutboundAdapter implements OrderRepository {

    private final LiverpoolOrderClient orderClient;
    private final LiverpoolItemClient itemClient;

    public OrderOutboundAdapter(LiverpoolOrderClient orderClient, LiverpoolItemClient itemClient) {
        this.orderClient = orderClient;
        this.itemClient = itemClient;
    }

    @Override
    public List<Order> fetchAllOrders() {
        return orderClient.getOrders().stream().map(dto -> 
            Order.builder()
                .orderRef(dto.getOrderRef())
                .userId(dto.getUserId())
                .storeName(dto.getStoreName())
                .orderStatus(dto.getOrderStatus())
                .salesChannel(dto.getSalesChannel())
                .items(dto.getItems() != null ? dto.getItems().stream()
                        .map(itemId -> Item.builder().itemId(itemId).build())
                        .collect(Collectors.toList()) : List.of()) 
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<Item> fetchAllItems() {
        return itemClient.getItems().stream().map(dto ->
            Item.builder()
                .itemId(dto.getItemId())
                .quantity(dto.getQuantity())
                .displayName(dto.getDisplayName())
                .build()
        ).collect(Collectors.toList());
    }
}