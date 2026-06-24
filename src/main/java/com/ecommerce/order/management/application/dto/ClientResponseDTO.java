package com.ecommerce.order.management.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    private String userId;
    private String fullName; 
    private String email;
    private String shippingAddress;
    private List<String> orders; 
}