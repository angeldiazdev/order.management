package com.ecommerce.order.management.domain.repository;

import java.util.Optional;

import com.ecommerce.order.management.domain.model.Client;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(String userId);
    Client update(Client client);
}