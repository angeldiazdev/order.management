package com.ecommerce.order.management.infrastructure.adapters.outbound.responseDTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExternalOrderResponse {
    private String orderRef;
    private String userId;
    private String storeName;
    private String orderStatus;
    @JsonProperty("canal") 
    private String salesChannel;
    private List<String> items;
}