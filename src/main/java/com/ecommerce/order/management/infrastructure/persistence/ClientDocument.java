package com.ecommerce.order.management.infrastructure.persistence;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clients")
public class ClientDocument {
    @Id
    private String userId;
    private String firstName;
    private String paternalLastName;
    private String maternalLastName;
    private String email;
    private String shippingAddress;
    private List<String> orders;
}