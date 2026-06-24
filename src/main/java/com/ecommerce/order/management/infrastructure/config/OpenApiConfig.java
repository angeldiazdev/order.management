package com.ecommerce.order.management.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce Order Management API")
                        .version("1.0.0")
                        .description("Technical assessment solution implementing Clean Architecture, MongoDB, and external API integrations."));
    }
}