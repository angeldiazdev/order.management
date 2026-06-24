package com.ecommerce.order.management.domain.repository;

import java.util.List;

import com.ecommerce.order.management.domain.model.Item;
import com.ecommerce.order.management.domain.model.Order;

public interface OrderRepository {
    List<Order> fetchAllOrders();
    List<Item> fetchAllItems();
}