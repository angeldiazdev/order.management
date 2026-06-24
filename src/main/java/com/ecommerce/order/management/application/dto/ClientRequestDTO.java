package com.ecommerce.order.management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {
    private String userId;
    private String firstName;
    private String paternalLastName;
    private String maternalLastName;
    private String email;
    private String shippingAddress;
}