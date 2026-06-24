package com.ecommerce.order.management.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.order.management.application.dto.ClientRequestDTO;
import com.ecommerce.order.management.application.dto.ClientResponseDTO;
import com.ecommerce.order.management.domain.model.Address;
import com.ecommerce.order.management.domain.model.Client;
import com.ecommerce.order.management.domain.repository.ClientRepository;

@Service
public class SaveClientUseCase {

    private final ClientRepository clientRepository;

    public SaveClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDTO execute(ClientRequestDTO request) {
        Client client = Client.builder()
                .userId(request.getUserId())
                .firstName(request.getFirstName())
                .paternalLastName(request.getPaternalLastName())
                .maternalLastName(request.getMaternalLastName())
                .email(request.getEmail())
                .address(Address.builder().shippingAddress(request.getShippingAddress()).build())
                .orders(List.of()) 
                .build();

        Client saved = clientRepository.save(client);

        return ClientResponseDTO.builder()
                .userId(saved.getUserId())
                .fullName(saved.getFirstName() + " " + saved.getPaternalLastName() + " " + saved.getMaternalLastName())
                .email(saved.getEmail())
                .shippingAddress(saved.getAddress() != null ? saved.getAddress().getShippingAddress() : null)
                .orders(saved.getOrders())
                .build();
    }
}