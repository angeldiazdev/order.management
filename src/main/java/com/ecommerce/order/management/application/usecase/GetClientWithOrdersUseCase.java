package com.ecommerce.order.management.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.order.management.application.dto.ClientResponseDTO;
import com.ecommerce.order.management.domain.model.Client;
import com.ecommerce.order.management.domain.model.Order;
import com.ecommerce.order.management.domain.repository.ClientRepository;
import com.ecommerce.order.management.domain.repository.OrderRepository;

@Service
public class GetClientWithOrdersUseCase {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public GetClientWithOrdersUseCase(ClientRepository clientRepository, OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    public ClientResponseDTO execute(String userId) {
        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + userId));
    
        List<Order> allExternalOrders = orderRepository.fetchAllOrders();

        List<String> userOrderRefs = allExternalOrders.stream()
                .filter(order -> order.getUserId() != null && order.getUserId().equals(client.getUserId()))
                .map(Order::getOrderRef)
                .collect(Collectors.toList());

        client.setOrders(userOrderRefs);
        clientRepository.update(client);

        return ClientResponseDTO.builder()
                .userId(client.getUserId())
                .fullName(client.getFirstName() + " " + client.getPaternalLastName() + " " + client.getMaternalLastName())
                .email(client.getEmail())
                .shippingAddress(client.getAddress() != null ? client.getAddress().getShippingAddress() : null)
                .orders(userOrderRefs)
                .build();
    }
}