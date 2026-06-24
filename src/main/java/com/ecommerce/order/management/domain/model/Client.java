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
public class Client {
    private String userId;
    private String firstName;
    private String paternalLastName;
    private String maternalLastName;
    private String email;
    private Address address;
    private List<String> orders;
}