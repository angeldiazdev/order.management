package com.ecommerce.order.management.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ecommerce.order.management.domain.model.Address;
import com.ecommerce.order.management.domain.model.Client;
import com.ecommerce.order.management.domain.repository.ClientRepository;

@Component
public class ClientPersistenceAdapter implements ClientRepository {

    private final SpringDataMongoRepository repository;

    public ClientPersistenceAdapter(SpringDataMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client save(Client client) {
        ClientDocument document = toDocument(client);
        ClientDocument saved = repository.save(document);
        return toDomain(saved);
    }

    @Override
    public Optional<Client> findById(String userId) {
        return repository.findById(userId).map(this::toDomain);
    }

    @Override
    public Client update(Client client) {
        ClientDocument document = toDocument(client);
        ClientDocument updated = repository.save(document);
        return toDomain(updated);
    }

    private ClientDocument toDocument(Client client) {
        return ClientDocument.builder()
                .userId(client.getUserId())
                .firstName(client.getFirstName())
                .paternalLastName(client.getPaternalLastName())
                .maternalLastName(client.getMaternalLastName())
                .email(client.getEmail())
                .shippingAddress(client.getAddress() != null ? client.getAddress().getShippingAddress() : null)
                .orders(client.getOrders())
                .build();
    }

    private Client toDomain(ClientDocument document) {
        return Client.builder()
                .userId(document.getUserId())
                .firstName(document.getFirstName())
                .paternalLastName(document.getPaternalLastName())
                .maternalLastName(document.getMaternalLastName())
                .email(document.getEmail())
                .address(Address.builder().shippingAddress(document.getShippingAddress()).build())
                .orders(document.getOrders())
                .build();
    }
}