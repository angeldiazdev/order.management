package com.ecommerce.order.management.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoRepository extends MongoRepository<ClientDocument, String> {
}